package org.simplon.TrouveTonMatch.service;

import jakarta.persistence.EntityNotFoundException;
import org.simplon.TrouveTonMatch.model.Projet;
import org.simplon.TrouveTonMatch.repository.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {

    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public List<Projet> findAll() {
        return projetRepository.findAll();
    }

    public Projet findById(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Projet non trouvé")
                );
    }

    public Projet save(Projet projet) {
        return projetRepository.save(projet);
    }

    public void deleteProjet(Long id) {
        if (!projetRepository.existsById(id)) {
            throw new EntityNotFoundException("Ce projet n'existe pas");
        }
        projetRepository.deleteById(id);
    }
}
