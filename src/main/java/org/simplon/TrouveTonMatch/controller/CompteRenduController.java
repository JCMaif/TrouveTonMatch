package org.simplon.TrouveTonMatch.controller;

import com.sun.security.auth.UserPrincipal;
import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.mapper.CompteRenduMapper;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.service.CompteRenduService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

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
    @PreAuthorize("hasRole('PARRAIN')")
    public ResponseEntity<CompteRenduDto> create(@RequestBody CompteRenduDto compteRenduDto) {
        CompteRenduDto saved = compteRenduService.saveCompteRendu(compteRenduDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<Page<CompteRenduDto>> findAll(@PageableDefault(size = 10, sort = "dateEchange", direction = DESC) Pageable pageable) {
        return ResponseEntity.ok(compteRenduService.findAllCompteRendu(pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Page<CompteRenduDto>> searchCompteRendus(
            @RequestParam(required = false) String projetTitle,
            @RequestParam(required = false) String PorteurLastname,
            @RequestParam(required = false) String ParrainLastname,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate prochainRdv,
            @PageableDefault(size = 10, sort = "dateEchange", direction = DESC) Pageable pageable
    ) {
        Page<CompteRenduDto> page = compteRenduService.searchWithFilters(projetTitle, PorteurLastname, ParrainLastname, prochainRdv, pageable);
        return ResponseEntity.ok(page);
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