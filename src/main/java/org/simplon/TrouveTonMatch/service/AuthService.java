package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.dtos.SignupDto;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void signUp(SignupDto data) throws Exception {
        if (userRepository.findByUsername(data.getUsername()) != null) {
            throw new Exception("User already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Utilisateur user = new Utilisateur(data.getUsername(), encryptedPassword, data.getRole(), null);
        userRepository.save(user);
    }
}
