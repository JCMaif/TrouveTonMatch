package org.simplon.TrouveTonMatch.config;

import org.simplon.TrouveTonMatch.model.UserApi;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
           if(userRepository.findByUsername("admin") == null) {
               UserApi admin = new UserApi("admin", passwordEncoder.encode("admin"), "ADMIN", null);
               userRepository.save(admin);
           }
    }
}
