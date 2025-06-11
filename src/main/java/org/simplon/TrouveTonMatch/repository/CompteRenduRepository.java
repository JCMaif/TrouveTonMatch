package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRenduRepository extends JpaRepository<CompteRendu, Long> {

    List<CompteRendu> findByPorteur(Porteur porteur);

    List<CompteRendu> findByProjetParrain(Parrain parrain);
}

