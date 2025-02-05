package org.simplon.TrouveTonMatch.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.simplon.TrouveTonMatch.dtos.*;
import org.simplon.TrouveTonMatch.exception.UserNotFoundException;
import org.simplon.TrouveTonMatch.exception.UsernameAlreadyExistsException;
import org.simplon.TrouveTonMatch.mapper.UtilisateurMapper;
import org.simplon.TrouveTonMatch.model.*;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PlateformeService plateformeService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UtilisateurMapper utilisateurMapper;
    private static final String DEFAULT_PASSWORD = "password321";

    public UserService(UserRepository userRepository, PlateformeService plateformeService, BCryptPasswordEncoder passwordEncoder, UtilisateurMapper utilisateurMapper) {
        this.userRepository = userRepository;
        this.plateformeService = plateformeService;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurMapper = utilisateurMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(utilisateurMapper::toDto).toList();
    }

    public List<UserDto> getAllUsersByPlateforme(Long plateformeId) {
        return userRepository.findByPlateformeId(plateformeId)
                .stream()
                .map(utilisateurMapper::toDto)
                .toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
        userRepository.deleteById(id);
    }

    public Optional<UserDto> findByUsername(String username) {
        Utilisateur user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec le username : " + username);
        }
        return Optional.ofNullable(utilisateurMapper.toDto(user));
    }

    public List<UserDto> getUsersByRoleAndPlateforme(UserRole role, Long plateformeId) {
        return userRepository.findByRoleAndPlateformeId(role, plateformeId).stream()
                .map(utilisateurMapper::toDto)
                .toList();
    }

    public UserDto findById(Long id) {
        return utilisateurMapper.toDto((userRepository.findById(id).orElseThrow()));
    }

    public SignupDto signUp(SignupDto data) {
        if (findByUsername(data.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);

        Utilisateur user = Utilisateur.builder()
                .username(data.getUsername())
                .password(encryptedPassword)
                .email(data.getEmail())
                .role(data.getRole())
                .enabled(false)
                .plateforme(data.getPlateforme())
                .build();

        userRepository.save(user);

        return new SignupDto(
                user.getUsername(),
                null,
                user.getEmail(),
                user.getRole(),
                user.getPlateforme()
        );
    }

    public void updatePassword(Long userId, String newPassword) {
        Utilisateur user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }



}
