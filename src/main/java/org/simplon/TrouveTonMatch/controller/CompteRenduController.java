package org.simplon.TrouveTonMatch.controller;

import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.service.CompteRenduService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compte-rendu")
public class CompteRenduController {

    private final CompteRenduService compteRenduService;

    public CompteRenduController(CompteRenduService compteRenduService) {
        this.compteRenduService = compteRenduService;
    }

    @PostMapping
    public ResponseEntity<CompteRenduDto> create(@RequestBody CompteRenduDto compteRenduDto) {
        CompteRenduDto saved = compteRenduService.saveCompteRendu(compteRenduDto);
        return ResponseEntity.ok().body(saved);
    }



}
