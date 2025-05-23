package org.simplon.TrouveTonMatch.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.simplon.TrouveTonMatch.dtos.DocumentDto;
import org.simplon.TrouveTonMatch.model.Document;
import org.simplon.TrouveTonMatch.model.DocumentMapper;
import org.simplon.TrouveTonMatch.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    public DocumentController(DocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getDocuments() {
        List<DocumentDto> documents = documentService.findAll();
        if (documents.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        Document document = documentService.findById(id);
        System.out.println("Document trouvé : " + document);
        System.out.println("Chemin du fichier : " + document.getPath());
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get("uploads/" + document.getPath());
            Resource fileResource = new UrlResource(filePath.toUri());
            System.out.println("Fichier lisible : " + fileResource.isReadable());

            if (!fileResource.exists() || !fileResource.isReadable()) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
                    .body(fileResource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF' )")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
       if (documentService.findById(id) == null) return ResponseEntity.notFound().build();
       documentService.deleteById(id);
       return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DocumentDto> uploadDocument(@RequestParam("file") MultipartFile file) {
        Document saved = documentService.save(file);
        DocumentDto dto = documentMapper.toDto(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
