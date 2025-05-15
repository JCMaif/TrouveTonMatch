package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
