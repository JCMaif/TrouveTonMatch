package org.simplon.TrouveTonMatch.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.simplon.TrouveTonMatch.dtos.ProjetCreateDto;
import org.simplon.TrouveTonMatch.dtos.ProjetDto;
import org.simplon.TrouveTonMatch.dtos.ProjetUpdateDto;
import org.simplon.TrouveTonMatch.mapper.ProjetMapper;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;
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
    private final UserService userService;
    private final UserRepository userRepository;

    public ProjetService(ProjetRepository projetRepository, SecurityUtils securityUtils, ProjetMapper projetMapper, UserService userService, UserRepository userRepository) {
        this.projetRepository = projetRepository;
        this.securityUtils = securityUtils;
        this.projetMapper = projetMapper;
        this.userService = userService;
        this.userRepository = userRepository;
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
        if (projetRepository.existsByPorteurId(projetCreateDto.porteurId())) {
            throw new IllegalArgumentException("Ce porteur a déjà un projet.");
        }

        Porteur porteur = userRepository.findById(projetCreateDto.porteurId())
                .filter(Porteur.class::isInstance)
                .map(Porteur.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("L'ID du porteur est invalide"));

        Projet projetToSave = projetMapper.toEntity(projetCreateDto, porteur);
        Projet projetSaved = projetRepository.save(projetToSave);

        return projetMapper.toCreateDto(projetSaved);
    }

    public void deleteProjet(Long id) {
        if (!projetRepository.existsById(id)) {
            throw new EntityNotFoundException("Ce projet n'existe pas");
        }
        projetRepository.deleteById(id);
    }

    public ProjetUpdateDto update(Long id, ProjetUpdateDto projetUpdateDto) {

        Projet projetToUpdate = findById(id);
        projetToUpdate.setTitle(projetUpdateDto.getTitle());
        projetToUpdate.setDescription(projetUpdateDto.getDescription());
        return projetMapper.toUpdateDto(projetRepository.save(projetToUpdate));
    }

    public boolean userCanEdit(Long projetId) {
        return projetRepository.findById(projetId)
                .map(Projet::getPorteur)
                .map(porteur -> userService.userCanEditOrDelete(porteur.getId()))
                .orElse(false);
    }

    @Transactional
    public ProjetDto affecterParrain(Long projetId, Long parrainId) {
        Projet projet = projetRepository.findById(projetId).orElseThrow(EntityNotFoundException::new);
        Parrain parrain = (Parrain) userRepository.findById(parrainId).orElseThrow(EntityNotFoundException::new);
        projet.setParrain(parrain);
        userService.verifierChargeParrain(parrain);
        return projetMapper.toDto(projetRepository.save(projet));
    }

}
