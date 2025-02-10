package org.simplon.TrouveTonMatch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.simplon.TrouveTonMatch.dtos.*;
import org.simplon.TrouveTonMatch.model.*;
import org.simplon.TrouveTonMatch.security.SecurityUtils;
import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, SecurityUtils securityUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        Long plateformeId = securityUtils.getAuthenticatedUserPlateformeId();

        List<UserDto> users = userService.getAllUsersByPlateforme(plateformeId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Utilisateur> editUserProfile(@PathVariable Long id, @RequestBody UserEditDto userDto) {
        try {
            Utilisateur updatedUser = userService.updateProfile(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-role")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDto>> getUserByRoleAndPlateformeId(@RequestParam String role) {
        Long plateformeId = securityUtils.getAuthenticatedUserPlateformeId();
        System.out.println("plateformeId : " + plateformeId);
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        List<UserDto> users = userService.getUsersByRoleAndPlateforme(userRole, plateformeId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupDto> createUser(@RequestBody SignupDto signupDto) {
        SignupDto createdUser = userService.signUp(signupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/{userId}/complete-profile")
    public ResponseEntity<Void> completeProfile(
            @PathVariable Long userId,
            @RequestBody UserEditDto userEditDto
    ) {
        userService.completeProfile(userId, userEditDto);
        return ResponseEntity.ok().build();
    }
}
