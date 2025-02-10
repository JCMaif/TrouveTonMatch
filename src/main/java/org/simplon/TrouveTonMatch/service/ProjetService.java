package org.simplon.TrouveTonMatch.service;

import jakarta.persistence.EntityNotFoundException;
import org.simplon.TrouveTonMatch.dtos.ProjetCreateDto;
import org.simplon.TrouveTonMatch.dtos.ProjetDto;
import org.simplon.TrouveTonMatch.mapper.ProjetMapper;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.ProjetRepository;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.simplon.TrouveTonMatch.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {

    private final ProjetRepository projetRepository;
    private final SecurityUtils securityUtils;
    private final ProjetMapper projetMapper;
    private final UserRepository userRepository;

    public ProjetService(ProjetRepository projetRepository, SecurityUtils securityUtils, ProjetMapper projetMapper, UserRepository userRepository) {
        this.projetRepository = projetRepository;
        this.securityUtils = securityUtils;
        this.projetMapper = projetMapper;
        this.userRepository = userRepository;
    }

    public List<Projet> findAll() {
        return projetRepository.findAll();
    }

    public List<ProjetDto> findAllByPlateformeId() {
        Long plateformeId = securityUtils.getAuthenticatedUserPlateformeId();

        return projetRepository.findByPlateformeId(plateformeId).stream()
                .map(projetMapper::toDto)
                .toList();
    }

    public Projet findById(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Projet non trouvé")
                );
    }

    public ProjetCreateDto save(ProjetCreateDto projetCreateDto) {
        Projet projetToSave = projetMapper.toEntity(projetCreateDto);

        Utilisateur utilisateur = userRepository.findById(projetCreateDto.porteurId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID: " + projetCreateDto.porteurId()));

        if (!(utilisateur instanceof Porteur porteur)) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + projetCreateDto.porteurId() + " n'est pas un Porteur.");
        }

        projetToSave.setPorteur(porteur);
        Projet projetSaved = projetRepository.save(projetToSave);

        return projetMapper.toCreateDto(projetSaved);
    }

    public void deleteProjet(Long id) {
        if (!projetRepository.existsById(id)) {
            throw new EntityNotFoundException("Ce projet n'existe pas");
        }
        projetRepository.deleteById(id);
    }
}
