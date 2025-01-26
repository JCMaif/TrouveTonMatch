package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/profil/edit")
    public ResponseEntity<UserDto> editUserProfile(Authentication authentication, @RequestBody UserDto userDto) {
        String username = authentication.getName();
        UserDto user = userService.updateProfile(username, userDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-role")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<List<UserDto>> getUserByRole(@RequestParam String role){
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        List<UserDto> users = userService.getUsersByRole(userRole);
        if (users.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }
}
