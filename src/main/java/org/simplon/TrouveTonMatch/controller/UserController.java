package org.simplon.TrouveTonMatch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.simplon.TrouveTonMatch.dtos.*;
import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.security.JwtService;
import org.simplon.TrouveTonMatch.service.UserService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        Long plateformeId = getAuthenticatedUserPlateformeId();

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

//    @PatchMapping("/{id}")
//    public ResponseEntity<UserCreateDto> editUserProfile(@PathVariable Long id, @RequestBody UserCreateDto userDto) {
//        try {
//            UserCreateDto updatedUser = userService.updateProfile(id, userDto);
//            return ResponseEntity.ok(updatedUser);
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(null);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
    //}

    @GetMapping("/by-role")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDto>> getUserByRoleAndPlateformeId(@RequestParam String role) {
        Long plateformeId = getAuthenticatedUserPlateformeId();
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

//    @PatchMapping("/{id}/finaliser")
//    public ResponseEntity<Map<String, String>> finaliserProfil(
//            @PathVariable Long id,
//            @RequestBody Map<String, Object> updates) {
//
//        UserDto user = userService.findById(id);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Utilisateur non trouvé"));
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        if (updates.containsKey("adresse") && updates.get("adresse") != null) {
//            try {
//                AdresseDto adresseDto = objectMapper.convertValue(updates.get("adresse"), AdresseDto.class);
//                user.setAdresse(adresseDto);
//            } catch (IllegalArgumentException e) {
//                return ResponseEntity.badRequest().body(Map.of("message", "Format d'adresse invalide"));
//            }
//        }
//
//        if (user instanceof ParrainDto parrainDto) {
//            if (updates.containsKey("parcours")) parrainDto.setParcours((String) updates.get("parcours"));
//            if (updates.containsKey("expertise")) parrainDto.setExpertise((String) updates.get("expertise"));
//            if (updates.containsKey("deplacement")) parrainDto.setDeplacement((String) updates.get("deplacement"));
//            if (updates.containsKey("disponibilite")) parrainDto.setDisponibilite((String) updates.get("disponibilite"));
//        } else if (user instanceof PorteurDto porteurDto) {
//            if (updates.containsKey("disponibilite")) porteurDto.setDisponibilite((String) updates.get("disponibilite"));
//        }
//
//        user.setEnabled(true);
//        passwordEncoder.encode(getPassw)
//        userService.updateProfile(id, user);
//
//        return ResponseEntity.ok(Map.of("message", "Profil finalisé avec succès"));
//    }

    private Long getAuthenticatedUserPlateformeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("❌ Authentication is null");
            return null;
        }

        System.out.println("✅ Authentication: " + authentication);

        if (authentication.getCredentials() == null) {
            System.out.println("❌ Credentials (token) is null");
            return null;
        }

        String token = authentication.getCredentials().toString();
        System.out.println("✅ Extracted Token: " + token);

        Long plateformeId = jwtService.extractPlateformeId(token);
        System.out.println("✅ Extracted PlateformeId: " + plateformeId);

        return plateformeId;
    }

}
