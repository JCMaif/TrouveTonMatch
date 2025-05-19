package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.model.MoyenEchange;
import org.simplon.TrouveTonMatch.model.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enum")
public class EnumController {

    @GetMapping("/roles")
    public List<String> getRoles() {

        return Arrays.stream(UserRole.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/moyens")
    public List<String> getMoyens() {
        return Arrays.stream(MoyenEchange.values())
                .map(Enum::name)
                .toList();
    }
}
