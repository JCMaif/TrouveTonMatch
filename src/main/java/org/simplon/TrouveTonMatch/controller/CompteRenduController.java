package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.mapper.CompteRenduMapper;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.service.CompteRenduService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<CompteRenduDto> create(@RequestBody CompteRenduDto compteRenduDto) {
        CompteRenduDto saved = compteRenduService.saveCompteRendu(compteRenduDto);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<List<CompteRenduDto>> findAll(){
        return ResponseEntity.ok(compteRenduService.findAllCompteRendu());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @compteRenduService.userCanEdit(#id)")
    public ResponseEntity<CompteRenduDto> findById(@PathVariable Long id){
        CompteRendu cr = compteRenduService.findById(id);
        if (cr == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(compteRenduMapper.toDto(cr));
    }

}
