package org.simplon.TrouveTonMatch.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.simplon.TrouveTonMatch.dtos.*;
import org.simplon.TrouveTonMatch.exception.UserNotFoundException;
import org.simplon.TrouveTonMatch.exception.UsernameAlreadyExistsException;
import org.simplon.TrouveTonMatch.mapper.UtilisateurMapper;
import org.simplon.TrouveTonMatch.model.*;
import org.simplon.TrouveTonMatch.repository.AdresseRepository;
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
    private final AdresseRepository adresseRepository;

    public UserService(UserRepository userRepository, PlateformeService plateformeService, BCryptPasswordEncoder passwordEncoder, UtilisateurMapper utilisateurMapper, AdresseRepository adresseRepository) {
        this.userRepository = userRepository;
        this.plateformeService = plateformeService;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurMapper = utilisateurMapper;
        this.adresseRepository = adresseRepository;
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

    public Utilisateur updateProfile(Long id, UserEditDto dto) {
        Utilisateur user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (dto.addresseId() != null) {
            Adresse adresse = adresseRepository.findById(dto.addresseId()).orElseThrow(EntityNotFoundException::new);
            user.setAdresse(adresse);
        }

        if (dto.rue() != null || dto.cpostal() != null || dto.ville() != null) {
            Adresse adresse = user.getAdresse();
            if (adresse == null) {
                adresse = new Adresse();
                user.setAdresse(adresse);
            }
            if (dto.rue() != null) {
                adresse.setRue(dto.rue());
            }
            if (dto.cpostal() != null) {
                adresse.setCpostal(dto.cpostal());
            }
            if (dto.ville() != null) {
                adresse.setVille(dto.ville());
            }
            adresseRepository.save(adresse);
        }

        if (dto.password() != null) {
            updatePassword(dto.id(), dto.password());
        }

        if (user instanceof Parrain) {
            Parrain parrain = (Parrain) user;
            if (dto.disponibilite() != null) {
                parrain.setDisponibilite(dto.disponibilite());
            }
            if (dto.parcours() != null) {
                parrain.setParcours(dto.parcours());
            }
            if (dto.expertise() != null) {
                parrain.setExpertise(dto.expertise());
            }
            if (dto.deplacement() != null) {
                parrain.setDeplacement(dto.deplacement());
            }
        } else if (user instanceof Porteur) {
            Porteur porteur = (Porteur) user;
            if (dto.disponibilite() != null) {
                porteur.setDisponibilite(dto.disponibilite());
            }
        }
        return userRepository.save(user);
    }

    @Transactional
    public void completeProfile(Long userId, UserEditDto userEditDto) {
        Utilisateur user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (Boolean.TRUE.equals(user.getEnabled())) {
            throw new IllegalStateException("Le profil est déjà complété.");
        }

        if (userEditDto.password() != null && !userEditDto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(userEditDto.password()));
        } else {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        Adresse adresse = (user.getAdresse() != null) ? user.getAdresse() : new Adresse();
        adresse.setRue(userEditDto.rue());
        adresse.setCpostal(userEditDto.cpostal());
        adresse.setVille(userEditDto.ville());
        user.setAdresse(adresse);

        if (user instanceof Porteur porteur) {
            porteur.setDisponibilite(userEditDto.disponibilite());
        } else if (user instanceof Parrain parrain) {
            parrain.setParcours(userEditDto.parcours());
            parrain.setExpertise(userEditDto.expertise());
            parrain.setDeplacement(userEditDto.deplacement());
            parrain.setDisponibilite(userEditDto.disponibilite());
        } else {
            throw new IllegalStateException("Rôle inconnu.");
        }

        user.setEnabled(true);

        userRepository.save(user);
    }

}
