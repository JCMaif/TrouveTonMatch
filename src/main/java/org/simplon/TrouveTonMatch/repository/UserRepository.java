package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);

    List<Utilisateur> findByRole(UserRole role);

    Utilisateur findByEmail(String email);

    List<Utilisateur> findByPlateformeId(Long plateformeId);

    @Query("SELECT u FROM Utilisateur u WHERE u.role = :role AND u.plateforme.id = :plateformeId")
    List<Utilisateur> findByRoleAndPlateformeId(@Param("role") UserRole role, @Param("plateformeId") Long plateformeId);

    List<Parrain> findByIsActiveTrue();
}

