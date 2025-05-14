package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    Optional<Projet> findById(Long id);

    @Query("""
        SELECT p FROM Projet p 
        JOIN p.porteur pr
        JOIN pr.plateforme pf
        WHERE pf.id = :plateformeId
    """)
    List<Projet> findByPlateformeId(@Param("plateformeId") Long plateformeId);

    boolean existsByPorteurId(Long aLong);

    int countByParrain(Parrain parrain);
}





