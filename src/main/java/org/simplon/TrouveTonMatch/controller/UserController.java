package org.simplon.TrouveTonMatch.controller;

import jakarta.annotation.security.PermitAll;
import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PermitAll
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
