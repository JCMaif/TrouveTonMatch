package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    @Query("""
        SELECT p FROM Projet p 
        JOIN p.porteur pr
        JOIN pr.plateforme pf
        WHERE pf.id = :plateformeId
    """)
    List<Projet> findByPlateformeId(@Param("plateformeId") Long plateformeId);
}





