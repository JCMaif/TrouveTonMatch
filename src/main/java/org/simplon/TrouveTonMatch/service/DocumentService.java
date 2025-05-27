package org.simplon.TrouveTonMatch.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.simplon.TrouveTonMatch.dtos.DocumentDto;
import org.simplon.TrouveTonMatch.model.Document;
import org.simplon.TrouveTonMatch.model.DocumentMapper;
import org.simplon.TrouveTonMatch.repository.DocumentRepository;
import org.simplon.TrouveTonMatch.security.SecurityUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final SecurityUtils securityUtils;
    private final Path UPLOAD_DIR = Paths.get("uploads/docs");

    public DocumentService(DocumentRepository documentRepository, DocumentMapper documentMapper, SecurityUtils securityUtils) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.securityUtils = securityUtils;
    }

    public List<DocumentDto> findAll() {
        return documentRepository.findAll().stream()
                .map(documentMapper::toDto)
                .toList();
    }

    public Document findById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id " + id + " not found"));

        String filePath = "uploads/" + document.getPath();
        File file = new File(filePath);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("Échec de la suppression du fichier : " + filePath);
            }
        }

        documentRepository.deleteById(id);
    }

    public Document save(MultipartFile file){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            if (!Files.exists(UPLOAD_DIR)) {
                Files.createDirectories(UPLOAD_DIR);
            }
            String originalFilename = Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName().toString();
            String baseName = originalFilename.contains(".")
                    ? originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                    : originalFilename;
            String extension = originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf('.'))
                    : "";

            Path destination = UPLOAD_DIR.resolve(originalFilename);
            String finalFilename = originalFilename;

            if (Files.exists(destination)) {
                String uniqueName = baseName + "-" + UUID.randomUUID() + extension;
                destination = UPLOAD_DIR.resolve(uniqueName);
                finalFilename = uniqueName;
            }

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            Long userId = securityUtils.getAuthenticatedUserId();

            Document document = new Document();
            document.setName(finalFilename);
            document.setType(file.getContentType());
            document.setPath(destination.toString().replace("\\", "/"));
            document.setUploadedAt(LocalDateTime.now());
            document.setUploadedBy(userId);

            return documentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("Error when saving a file", e);
        }
    }

    public ResponseEntity<Resource> prepareDocumentDownload(Long id) {
        Document document = findById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get("uploads/" + document.getPath());
            Resource fileResource = new UrlResource(filePath.toUri());

            if (!fileResource.exists() || !fileResource.isReadable()) {
                return ResponseEntity.badRequest().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String fileName = extractFileName(document.getPath());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileResource);

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private String extractFileName(String path) {
        if (path == null) return "document";
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
