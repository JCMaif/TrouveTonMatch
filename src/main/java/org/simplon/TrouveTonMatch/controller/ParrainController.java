package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/parrains")
@RestController
public class ParrainController {
    private final UserService userService;

    public ParrainController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{parrainId}/toggle-active")
    public ResponseEntity<Void> toggleActive(@PathVariable Long parrainId) {
        userService.toggleParrainActive(parrainId);
        return ResponseEntity.ok().build();
    }
}
