package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.AffecterParrainDto;
import org.simplon.TrouveTonMatch.dtos.ProjetCreateDto;
import org.simplon.TrouveTonMatch.dtos.ProjetDto;
import org.simplon.TrouveTonMatch.dtos.ProjetUpdateDto;
import org.simplon.TrouveTonMatch.mapper.ProjetMapper;
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
    private final ProjetMapper projetMapper;

    public ProjetController(ProjetService projetService, ProjetMapper projetMapper) {
        this.projetService = projetService;
        this.projetMapper = projetMapper;
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProjetDto>> findAll() {
        List<ProjetDto> projets = projetService.findAllByPlateformeId();
        if (projets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projets);
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjetDto> findById(@PathVariable Long id) {
        Projet projet = projetService.findById(id);

        if (projet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projetMapper.toDto(projet));
    }

    @PostMapping
    @PreAuthorize("hasRole('PORTEUR')")
    public ResponseEntity<ProjetCreateDto> create(@RequestBody ProjetCreateDto projetCreateDto) {

        ProjetCreateDto projetSaved = projetService.save(projetCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(projetSaved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Projet> delete(@PathVariable Long id) {
        Projet projet = projetService.findById(id);
        if (projet == null) {
            return ResponseEntity.notFound().build();
        }
        projetService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @projetService.userCanEdit(#id)")
    public ResponseEntity<ProjetUpdateDto> update(@PathVariable Long id, @RequestBody ProjetUpdateDto projetUpdateDto) {

        ProjetUpdateDto projetUpdated = projetService.update(id, projetUpdateDto);
        return ResponseEntity.ok(projetUpdated);
    }

    @PutMapping("/{id}/affecter-parrain")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @projetService.userCanEdit(#id)")
    public ResponseEntity<ProjetDto> affecterParrain(@PathVariable Long id, @RequestBody AffecterParrainDto dto) {

        ProjetDto projetUpdated = projetService.affecterParrain(id, dto.parrainId());
        return ResponseEntity.ok(projetUpdated);
    }
}
