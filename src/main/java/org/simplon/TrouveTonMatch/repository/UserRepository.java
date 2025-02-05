package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);

    List<Utilisateur> findByRole(UserRole role);

    Utilisateur findByEmail(String email);

    List<Utilisateur> findByPlateformeId(Long plateformeId);

    List<Utilisateur> findByRoleAndPlateformeId(UserRole role, Long plateformeId);
}

