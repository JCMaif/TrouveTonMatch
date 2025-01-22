package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.model.Plateforme;
import org.simplon.TrouveTonMatch.repository.PlateformeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateformeService {

    private final PlateformeRepository plateformeRepository;

    public PlateformeService(PlateformeRepository plateformeRepository) {
        this.plateformeRepository = plateformeRepository;
    }

    public List<Plateforme> findAll() {
        return plateformeRepository.findAll();
    }

    public Plateforme findById(Long id) {
        validateExistenceById(id);
        return plateformeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Plateforme with ID " + id + " not found.")
        );
    }

    public Plateforme save(Plateforme plateforme) {
        validateNotNull(plateforme, "Plateforme must not be null.");
        return plateformeRepository.save(plateforme);
    }

    public void delete(Long id) {
        validateExistenceById(id);
        plateformeRepository.deleteById(id);
    }

    public void validateExistenceById(Long id) {
        if (id == null || !plateformeRepository.existsById(id)) {
            throw new IllegalArgumentException("Plateforme with ID " + id + " does not exist.");
        }
    }

    private void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
