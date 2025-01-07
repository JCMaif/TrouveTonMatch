package org.simplon.TrouveTonMatch.repository;

import org.simplon.TrouveTonMatch.model.UserApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserApi, Long> {
    UserApi findByUsername(String username);
}
