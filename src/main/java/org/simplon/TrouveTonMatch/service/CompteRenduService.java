package org.simplon.TrouveTonMatch.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.mapper.CompteRenduMapper;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.CompteRenduRepository;
import org.simplon.TrouveTonMatch.specification.CompteRenduSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public CompteRenduService(
            CompteRenduRepository compteRenduRepository,
            UserService userService,
            ProjetService projetService,
            CompteRenduMapper compteRenduMapper
    ) {
        this.compteRenduRepository = compteRenduRepository;
        this.userService = userService;
        this.projetService = projetService;
        this.compteRenduMapper = compteRenduMapper;
    }

    public CompteRenduDto saveCompteRendu(CompteRenduDto dto) {
        Utilisateur user = userService.getCurrentUser();
        log.info("user : {}", user);

        if (!(user instanceof Parrain parrain)) {
            throw new SecurityException("Seuls les parrains peuvent créer un compte-rendu");
        }

        Projet projet = projetService.findById(dto.projetId());
        if (projet == null) {
            throw new EntityNotFoundException("Projet introuvable");
        }

        if (!parrain.equals(projet.getParrain())) {
            throw new SecurityException("Vous ne pouvez rédiger un compte-rendu que pour les projets que vous parrainez.");
        }

        CompteRendu compteRendu = compteRenduMapper.toEntity(dto);
        compteRendu.setProjet(projet);
        compteRendu.setParrain(parrain);
        compteRenduRepository.save(compteRendu);

        return compteRenduMapper.toDto(compteRendu);
    }

    public Page<CompteRenduDto> findAllCompteRendu(Pageable pageable) {
        Utilisateur user = userService.getCurrentUser();

        Page<CompteRendu> compteRendus;

        if (user instanceof Porteur porteur) {
            compteRendus = compteRenduRepository.findByProjet_Porteur(porteur, pageable);
        } else if (user instanceof Parrain parrain) {
            compteRendus = compteRenduRepository.findByParrain(parrain, pageable);
        } else if (userService.isAdminOrStaff(user)) {
            compteRendus = compteRenduRepository.findAll(pageable);
        } else {
            throw new SecurityException("Vous n'avez pas la permission de voir les comptes-rendus");
        }

        return compteRendus.map(compteRenduMapper::toDto);
    }

    public Page<CompteRenduDto> searchWithFilters(String projetTitle, String PorteurLastname, String ParrainLastname, LocalDate prochainRdv, Pageable pageable) {
        Specification<CompteRendu> spec = Specification
                .where(CompteRenduSpecification.hasProjetTitle(projetTitle))
                .and(CompteRenduSpecification.hasPorteurLastname(PorteurLastname))
                .and(CompteRenduSpecification.hasParrainLastname(ParrainLastname))
                .and(CompteRenduSpecification.hasProchainRdv(prochainRdv));

        return compteRenduRepository.findAll(spec, pageable)
                .map(compteRenduMapper::toDto);
    }

    public CompteRenduDto findById(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<CompteRendu> compteRenduOpt;

        if (user instanceof Porteur porteur) {
            compteRenduOpt = compteRenduRepository.findByIdAndProjet_Porteur(id, porteur);
        } else if (user instanceof Parrain parrain) {
            compteRenduOpt = compteRenduRepository.findByIdAndParrain(id, parrain);
        } else if (userService.isAdminOrStaff(user)) {
            compteRenduOpt = compteRenduRepository.findById(id); // Admin ou staff : pas de restriction
        } else {
            throw new SecurityException("Vous n'avez pas la permission d'accéder à ce compte-rendu");
        }

        CompteRendu compteRendu = compteRenduOpt
                .orElseThrow(() -> new SecurityException("Vous n'avez pas la permission d'accéder à ce compte-rendu"));

        return compteRenduMapper.toDto(compteRendu);
    }

    @Transactional
    public CompteRenduDto update(Long id, CompteRenduDto dto) {
        Utilisateur user = userService.getCurrentUser();
        Optional<CompteRendu> compteRenduOpt;

        if (user instanceof Porteur porteur) {
            compteRenduOpt = compteRenduRepository.findByIdAndProjet_Porteur(id, porteur);
        } else if (user instanceof Parrain parrain) {
            compteRenduOpt = compteRenduRepository.findByIdAndParrain(id, parrain);
        } else if (userService.isAdminOrStaff(user)) {
            compteRenduOpt = compteRenduRepository.findById(id);
        } else {
            throw new SecurityException("Vous n'avez pas la permission de modifier ce compte-rendu");
        }

        CompteRendu compteRendu = compteRenduOpt
                .orElseThrow(() -> new SecurityException("Vous n'avez pas la permission de modifier ce compte-rendu"));

        return compteRenduMapper.toDto(compteRenduRepository.save(compteRendu));
    }

    @Transactional
    public void deleteById(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<CompteRendu> compteRenduOpt;

        if (user instanceof Porteur porteur) {
            compteRenduOpt = compteRenduRepository.findByIdAndProjet_Porteur(id, porteur);
        } else if (user instanceof Parrain parrain) {
            compteRenduOpt = compteRenduRepository.findByIdAndParrain(id, parrain);
        } else if (userService.isAdminOrStaff(user)) {
            compteRenduOpt = compteRenduRepository.findById(id);
        } else {
            throw new SecurityException("Vous n'avez pas la permission de supprimer ce compte-rendu");
        }

        CompteRendu compteRendu = compteRenduOpt
                .orElseThrow(() -> new SecurityException("Vous n'avez pas la permission de supprimer ce compte-rendu"));

        compteRenduRepository.delete(compteRendu);
    }

    public boolean userCanEdit(Long crId) {
        Utilisateur user = userService.getCurrentUser();
        if (userService.isAdminOrStaff(user)) return true;

        return compteRenduRepository.findById(crId)
                .map(compteRendu -> {
                    if (user instanceof Parrain) {
                        return compteRendu.getParrain().getId().equals(user.getId());
                    }
                    return false;
                })
                .orElse(false);
    }
}