package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findBySenderId(Long senderId);
}
