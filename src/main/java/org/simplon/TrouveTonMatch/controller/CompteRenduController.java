package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.mapper.CompteRenduMapper;
import org.simplon.TrouveTonMatch.service.CompteRenduService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/compte-rendu")
public class CompteRenduController {

    private final CompteRenduService compteRenduService;


    public CompteRenduController(CompteRenduService compteRenduService) {
        this.compteRenduService = compteRenduService;
    }

    @PostMapping
    @PreAuthorize("hasRole('PARRAIN')")
    public ResponseEntity<CompteRenduDto> create(@RequestBody CompteRenduDto compteRenduDto) {
        CompteRenduDto saved = compteRenduService.saveCompteRendu(compteRenduDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<Page<CompteRenduDto>> findAll(@PageableDefault(size = 10, sort = "dateEchange", direction = DESC) Pageable pageable) {
        return ResponseEntity.ok(compteRenduService.findAllCompteRendu(pageable));
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
        CompteRenduDto updated = compteRenduService.update(id, compteRenduDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compteRenduService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}