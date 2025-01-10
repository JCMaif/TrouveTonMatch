package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
}
