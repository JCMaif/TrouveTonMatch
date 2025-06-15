package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRenduRepository extends JpaRepository<CompteRendu, Long>, JpaSpecificationExecutor<CompteRendu> {

    @EntityGraph(attributePaths = {"projet", "projet.porteur", "parrain"})
    Page<CompteRendu> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"projet", "projet.porteur", "parrain"})
    Page<CompteRendu> findByProjet_Porteur(Porteur porteur, Pageable pageable);

    @EntityGraph(attributePaths = {"projet", "projet.porteur", "parrain"})
    Page<CompteRendu> findByParrain(Parrain parrain, Pageable pageable);

    @EntityGraph(attributePaths = {"projet", "projet.porteur", "parrain"})
    Optional<CompteRendu> findByIdAndProjet_Porteur(Long id, Porteur porteur);

    @EntityGraph(attributePaths = {"projet", "projet.porteur", "parrain"})
    Optional<CompteRendu> findByIdAndParrain(Long id, Parrain parrain);
}

