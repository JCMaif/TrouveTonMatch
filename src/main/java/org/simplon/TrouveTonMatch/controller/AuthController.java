package org.simplon.TrouveTonMatch.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.simplon.TrouveTonMatch.dtos.JwtDto;
import org.simplon.TrouveTonMatch.dtos.SignInDto;
import org.simplon.TrouveTonMatch.dtos.SignupDto;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.security.JwtService;
import org.simplon.TrouveTonMatch.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto data) throws Exception {
        authService.signUp(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<JwtDto> signIn(@RequestBody @Valid SignInDto data) throws Exception {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        Authentication authUser = authenticationManager.authenticate(usernamePassword);
        String token = jwtService.generateToken((Utilisateur) authUser.getPrincipal());
        return ResponseEntity.ok().body(new JwtDto(token));
    }

    @PostMapping("/logout")
    @PermitAll
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
