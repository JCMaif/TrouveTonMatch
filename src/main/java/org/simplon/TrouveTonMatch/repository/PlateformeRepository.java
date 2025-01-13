package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Plateforme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlateformeRepository extends JpaRepository<Plateforme, Long> {
    boolean existsById(Long id);
    Optional<Plateforme> findByNom(String name);
}
