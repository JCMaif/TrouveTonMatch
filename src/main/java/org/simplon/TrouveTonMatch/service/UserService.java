package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto findByUsername(String username) {
        return UserDto.fromEntity(userRepository.findByUsername(username));
    }

    public List<UserDto> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream().map(UserDto::fromEntity).toList();

    }
}
