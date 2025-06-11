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
        compteRendu.setProjet(projet);
        compteRenduRepository.save(compteRendu);
        return compteRenduMapper.toDto(compteRendu);
    }

    /*
    * Les compte-rendus peuvent être lus par le porteur du projet, le parrain associé, ADMIN et STAFF
    *
    * */
    public List<CompteRenduDto> findAllCompteRendu() {
        Utilisateur user = userService.getCurrentUser();
        List<CompteRendu> compteRendus;

        if (user instanceof Porteur) {
            // Un porteur ne peut voir que ses propres comptes-rendus
            Porteur porteur = (Porteur) user;
            compteRendus = compteRenduRepository.findByPorteur(porteur);
        } else if (user instanceof Parrain) {
            // Un parrain peut voir les comptes-rendus des projets qu'il parraine
            Parrain parrain = (Parrain) user;
            compteRendus = compteRenduRepository.findByProjetParrain(parrain);
        } else if (userService.isAdminOrStaff(user)) {
            // Les admins et staff peuvent voir tous les comptes-rendus
            compteRendus = compteRenduRepository.findAll();
        } else {
            throw new SecurityException("Vous n'avez pas la permission de voir les comptes-rendus");
        }

        return compteRendus.stream()
                .map(compteRenduMapper::toDto)
                .toList();
    }


    public boolean userCanEdit(Long crId) {
        Utilisateur user = userService.getCurrentUser();
        if (userService.isAdminOrStaff(user)) {
            return true;
        }

        return compteRenduRepository.findById(crId)
                .map(compteRendu -> {
                    if (user instanceof Porteur) {
                        return compteRendu.getPorteur().getId().equals(user.getId());
                    } else if (user instanceof Parrain) {
                        return compteRendu.getProjet().getParrain() != null &&
                                compteRendu.getProjet().getParrain().getId().equals(user.getId());
                    }
                    return false;
                })
                .orElse(false);
    }

    public CompteRenduDto findById(Long id) {
        CompteRendu compteRendu = compteRenduRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compte-rendu introuvable"));

        Utilisateur user = userService.getCurrentUser();
        boolean canAccess = false;

        if (user instanceof Porteur) {
            canAccess = compteRendu.getPorteur().getId().equals(user.getId());
        } else if (user instanceof Parrain) {
            Parrain parrain = (Parrain) user;
            canAccess = compteRendu.getProjet().getParrain() != null &&
                    compteRendu.getProjet().getParrain().getId().equals(user.getId());
        } else if (userService.isAdminOrStaff(user)) {
            canAccess = true;
        }

        if (!canAccess) {
            throw new SecurityException("Vous n'avez pas la permission d'accéder à ce compte-rendu");
        }

        return compteRenduMapper.toDto(compteRendu);
    }

    public CompteRenduDto updateCompteRendu(Long id, CompteRenduDto compteRenduDto) {
        if (compteRenduDto == null) {
            throw new IllegalArgumentException("Le compte-rendu DTO ne peut pas être nul");
        }

        if (!userCanEdit(id)) {
            throw new SecurityException("Vous n'avez pas la permission de modifier ce compte-rendu");
        }

        CompteRendu existingCompteRendu = compteRenduRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compte-rendu introuvable"));

        compteRenduMapper.partialUpdate(existingCompteRendu, compteRenduDto);
        CompteRendu updatedCompteRendu = compteRenduRepository.save(existingCompteRendu);
        return compteRenduMapper.toDto(updatedCompteRendu);
    }

    public void deleteCompteRendu(Long id) {
        if (!userCanEdit(id)) {
            throw new SecurityException("Vous n'avez pas la permission de supprimer ce compte-rendu");
        }

        if (!compteRenduRepository.existsById(id)) {
            throw new EntityNotFoundException("Compte-rendu introuvable");
        }

        compteRenduRepository.deleteById(id);
    }
}
