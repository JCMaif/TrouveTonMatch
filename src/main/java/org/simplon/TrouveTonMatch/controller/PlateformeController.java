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
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Plateforme>> findAll() {
        List<Plateforme> plateformes = plateformeService.findAll();
        return ResponseEntity.ok(plateformes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plateforme> findById(@PathVariable Long id) {
        return ResponseEntity.ok(plateformeService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Plateforme> create(@RequestBody Plateforme plateforme) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plateformeService.save(plateforme));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
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
