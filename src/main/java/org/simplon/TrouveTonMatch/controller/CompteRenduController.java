package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.mapper.CompteRenduMapper;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.service.CompteRenduService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/compte-rendu")
public class CompteRenduController {

    private final CompteRenduService compteRenduService;
    private final CompteRenduMapper compteRenduMapper;

    public CompteRenduController(CompteRenduService compteRenduService, CompteRenduMapper compteRenduMapper) {
        this.compteRenduService = compteRenduService;
        this.compteRenduMapper = compteRenduMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'PORTEUR')")
    public ResponseEntity<CompteRenduDto> create(@RequestBody CompteRenduDto compteRenduDto) {
        CompteRenduDto saved = compteRenduService.saveCompteRendu(compteRenduDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'PORTEUR', 'PARRAIN')")
    public ResponseEntity<List<CompteRenduDto>> findAll() {
        return ResponseEntity.ok(compteRenduService.findAllCompteRendu());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<CompteRenduDto> findById(@PathVariable Long id) {
        CompteRenduDto crDto = compteRenduService.findById(id);
        return ResponseEntity.ok(crDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<CompteRenduDto> update(@PathVariable Long id, @RequestBody CompteRenduDto compteRenduDto) {
        CompteRenduDto updated = compteRenduService.updateCompteRendu(id, compteRenduDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compteRenduService.deleteCompteRendu(id);
        return ResponseEntity.noContent().build();
    }
}

