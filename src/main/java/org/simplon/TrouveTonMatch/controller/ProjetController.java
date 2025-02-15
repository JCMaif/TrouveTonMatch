package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.ProjetCreateDto;
import org.simplon.TrouveTonMatch.dtos.ProjetDto;
import org.simplon.TrouveTonMatch.model.Projet;
import org.simplon.TrouveTonMatch.service.ProjetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projet")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

//    @GetMapping
//    public ResponseEntity<List<Projet>> findAll() {
//        List<Projet> projets = projetService.findAll();
//        return ResponseEntity.ok(projets);
//    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProjetDto>> findAll() {
        List<ProjetDto> projets = projetService.findAllByPlateformeId();
        if (projets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(projets);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Projet> findById(@PathVariable Long id) {
        Projet projet = projetService.findById(id);
        if (projet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projet);
    }

    @PostMapping
    public ResponseEntity<ProjetCreateDto> create(@RequestBody ProjetCreateDto projetCreateDto) {
        ProjetCreateDto projetSaved = projetService.save(projetCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(projetSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Projet> delete(@PathVariable Long id) {
        Projet projet = projetService.findById(id);
        if (projet == null) {
            return ResponseEntity.notFound().build();
        }
        projetService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }
}
