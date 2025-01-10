//package org.simplon.TrouveTonMatch.config;
//
//import org.simplon.TrouveTonMatch.model.Utilisateur;
//import org.simplon.TrouveTonMatch.model.UserRole;
//import org.simplon.TrouveTonMatch.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class UserInitializer {
//
//    @Bean
//    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByUsername("admin") == null) {
//                Utilisateur admin = new Utilisateur();
//                admin.setUsername("admin");
//                admin.setEmail("admin@admin.com");
//                admin.setPassword(passwordEncoder.encode("admin"));
//                admin.setRole(UserRole.ADMIN);
//                userRepository.save(admin);
//            }
//            if (userRepository.findByUsername("user") == null) {
//                Utilisateur user = new Utilisateur();
//                user.setUsername("user");
//                user.setEmail("user@user.com");
//                user.setPassword(passwordEncoder.encode("user"));
//                user.setRole(UserRole.USER);
//                userRepository.save(user);
//            }
//
//        };
//    }
//}
