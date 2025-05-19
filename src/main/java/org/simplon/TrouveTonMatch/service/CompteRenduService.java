package org.simplon.TrouveTonMatch.service;

import java.util.List;

import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.mapper.CompteRenduMapper;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.CompteRenduRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompteRenduService {

    private final CompteRenduRepository compteRenduRepository;
    private final UserService userService;
    private final ProjetService projetService;
    private final CompteRenduMapper compteRenduMapper;

    public CompteRenduService(CompteRenduRepository compteRenduRepository, UserService userService, ProjetService projetService,
            CompteRenduMapper compteRenduMapper) {
        this.compteRenduRepository = compteRenduRepository;
        this.userService = userService;
        this.projetService = projetService;
        this.compteRenduMapper = compteRenduMapper;
    }

    /*
    Un compte-rendu ne peut être créé que si :
    - l'utilisateur connecté est un Porteur
    - que ce Porteur a un projet
    - que ce projet a un Parrain
     */
    public CompteRenduDto saveCompteRendu(CompteRenduDto compteRenduDto) {
        Utilisateur user = userService.getCurrentUser();
        log.info("user : {}", user);
        if (!(user instanceof Porteur)) {
            throw new SecurityException("Seuls les porteurs peuvent créer ou modifier un compte-rendu");
        }
        Projet projet = ((Porteur) user).getProjet();
        if (projet == null) throw new EntityNotFoundException("Ce Projet n'existe pas");
        if (!(projet.getPorteur().getId().equals(user.getId()))) throw new SecurityException("Ce projet n'appartient pas au porteur connecté");
        log.info("projet : {}", projet);

        Parrain parrain = projet.getParrain();
        if (parrain == null) throw new EntityNotFoundException("Ce projet n'a pas encore de parrain");
        log.info("parrain : {}", parrain);

        CompteRendu compteRendu = compteRenduMapper.toEntity(compteRenduDto);
        compteRendu.setPorteur((Porteur) user);
        compteRenduRepository.save(compteRendu);
        return compteRenduMapper.toDto(compteRendu);
    }

    public List<CompteRenduDto> findAllCompteRendu() {
        return compteRenduMapper.toDto(compteRenduRepository.findAll());
    }

}
