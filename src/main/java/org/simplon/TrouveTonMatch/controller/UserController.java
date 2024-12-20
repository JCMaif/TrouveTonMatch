package org.simplon.TrouveTonMatch.controller;

import jakarta.annotation.security.PermitAll;
import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("Returning users: " + userService.getAllUsers());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profil")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication) {
        String username = authentication.getName();
        UserDto user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profil/edit")
    public ResponseEntity<UserDto> editUserProfile(Authentication authentication) {
        String username = authentication.getName();
        UserDto user = userService.findByUsername(username);

        return ResponseEntity.ok(user);
    }
}
