package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).toList();
    }
}
