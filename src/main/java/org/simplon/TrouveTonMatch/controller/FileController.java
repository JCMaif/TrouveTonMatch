package org.simplon.TrouveTonMatch.controller;

import jakarta.persistence.EntityNotFoundException;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/uploads")
@PreAuthorize("isAuthenticated()")
public class FileController {

    private final UserRepository userRepository;
    private final Path profileUploadDir = Paths.get("uploads/profiles");

    public FileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get("uploads/profiles/" + filename);
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(resource);
    }

    @PostMapping("profiles/{userId}")
    public ResponseEntity<Map<String, String>> uploadProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Le fichier n'est pas une image valide"));
            }

            Utilisateur user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

            if (user.getProfilePicture() != null) {
                Files.deleteIfExists(profileUploadDir.resolve(user.getProfilePicture()));
            }

            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = profileUploadDir.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setProfilePicture(filename);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("profilePicture", filename));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de l'upload."));
        }

    }
}
