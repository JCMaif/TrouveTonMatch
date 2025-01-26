package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);

    List<Utilisateur> findByRole(UserRole role);
}

