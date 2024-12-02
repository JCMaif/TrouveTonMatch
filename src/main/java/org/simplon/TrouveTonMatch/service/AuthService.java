package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.dtos.SignupDto;
import org.simplon.TrouveTonMatch.model.UserApi;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public UserApi signUp(SignupDto data) throws Exception {
        if (userRepository.findByUsername(data.getUsername()) != null) {
            throw new Exception("User already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        UserApi user = new UserApi(data.getUsername(), encryptedPassword, data.getRole());
        return userRepository.save(user);
    }
}
