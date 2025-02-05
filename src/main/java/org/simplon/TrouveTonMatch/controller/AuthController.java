package org.simplon.TrouveTonMatch.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.simplon.TrouveTonMatch.dtos.JwtDto;
import org.simplon.TrouveTonMatch.dtos.SignInDto;
import org.simplon.TrouveTonMatch.dtos.SignupDto;
import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.security.JwtService;
import org.simplon.TrouveTonMatch.service.AuthService;
import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto data) {
        try {
            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());

            Authentication authUser = authenticationManager.authenticate(usernamePassword);
            Utilisateur utilisateur = (Utilisateur) authUser.getPrincipal();

            String token = jwtService.generateToken(utilisateur);
            return ResponseEntity.ok(new JwtDto(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Identifiants incorrects"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Erreur interne"));
        }
    }

    @PostMapping("/logout")
    @PermitAll
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
