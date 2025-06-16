package org.simplon.TrouveTonMatch.controller;

import java.util.List;

import org.simplon.TrouveTonMatch.dtos.DocumentDto;
import org.simplon.TrouveTonMatch.model.Document;
import org.simplon.TrouveTonMatch.mapper.DocumentMapper;
import org.simplon.TrouveTonMatch.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
        return documentService.prepareDocumentDownload(id);
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
