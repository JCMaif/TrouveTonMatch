package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.model.Plateforme;
import org.simplon.TrouveTonMatch.service.PlateformeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plateforme")
public class PlateformeController {

    private final PlateformeService plateformeService;

    public PlateformeController(PlateformeService plateformeService) {
        this.plateformeService = plateformeService;
    }

    @GetMapping
    public ResponseEntity<List<Plateforme>> findAll() {
        List<Plateforme> plateformes = plateformeService.findAll();
        return ResponseEntity.ok(plateformes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plateforme> findById(@PathVariable Long id) {
        Plateforme plateforme = plateformeService.findById(id);
        return ResponseEntity.ok(plateforme);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Plateforme> create(@RequestBody Plateforme plateforme) {
        Plateforme createdPlateforme = plateformeService.save(plateforme);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlateforme);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PLATEFORME')")
    public ResponseEntity<Plateforme> update(@PathVariable Long id, @RequestBody Plateforme plateforme) {
        plateformeService.validateExistenceById(id);
        plateforme.setId(id);
        Plateforme updatedPlateforme = plateformeService.save(plateforme);
        return ResponseEntity.ok(updatedPlateforme);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        plateformeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
