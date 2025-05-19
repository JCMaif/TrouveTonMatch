package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRenduRepository extends JpaRepository<CompteRendu, Long> {
}
