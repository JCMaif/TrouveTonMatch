package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.PlateformeDto;
import org.simplon.TrouveTonMatch.mapper.PlateformeMapper;
import org.simplon.TrouveTonMatch.model.Plateforme;
import org.simplon.TrouveTonMatch.service.PlateformeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plateforme")
public class PlateformeController {

    private final PlateformeService plateformeService;
    private final PlateformeMapper plateformeMapper;

    public PlateformeController(PlateformeService plateformeService, PlateformeMapper plateformeMapper) {
        this.plateformeService = plateformeService;
        this.plateformeMapper = plateformeMapper;
    }

    @GetMapping
    public ResponseEntity<List<PlateformeDto>> findAll() {
        List<Plateforme> plateformes = plateformeService.findAll();
        List<PlateformeDto> dtos = plateformes.stream()
                .map(plateformeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlateformeDto> findById(@PathVariable Long id) {
        Plateforme plateforme = plateformeService.findById(id);
        PlateformeDto dto = plateformeMapper.toDto(plateforme);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlateformeDto> create(@RequestBody Plateforme plateforme) {
        Plateforme createdPlateforme = plateformeService.save(plateforme);
        PlateformeDto dto = plateformeMapper.toDto(createdPlateforme);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PLATEFORME')")
    public ResponseEntity<PlateformeDto> update(@PathVariable Long id, @RequestBody Plateforme plateforme) {
        plateformeService.validateExistenceById(id);
        plateforme.setId(id);
        Plateforme updatedPlateforme = plateformeService.save(plateforme);
        PlateformeDto dto = plateformeMapper.toDto(updatedPlateforme);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        plateformeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
