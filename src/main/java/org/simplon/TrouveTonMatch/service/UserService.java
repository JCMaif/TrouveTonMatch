package org.simplon.TrouveTonMatch.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.simplon.TrouveTonMatch.dtos.*;
import org.simplon.TrouveTonMatch.exception.UsernameAlreadyExistsException;
import org.simplon.TrouveTonMatch.exception.UserNotFoundException;
import org.simplon.TrouveTonMatch.mapper.UtilisateurMapper;
import org.simplon.TrouveTonMatch.model.*;
import org.simplon.TrouveTonMatch.repository.AdresseRepository;
import org.simplon.TrouveTonMatch.repository.ProjetRepository;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PlateformeService plateformeService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper utilisateurMapper;
    private static final String DEFAULT_PASSWORD = "password321";
    private final AdresseRepository adresseRepository;
    private final ProjetRepository projetRepository;

    public UserService(UserRepository userRepository, PlateformeService plateformeService, PasswordEncoder passwordEncoder, UtilisateurMapper utilisateurMapper, AdresseRepository adresseRepository, ProjetRepository projetRepository) {
        this.userRepository = userRepository;
        this.plateformeService = plateformeService;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurMapper = utilisateurMapper;
        this.adresseRepository = adresseRepository;
        this.projetRepository = projetRepository;
    }

    public List<UserDto> getAllUsersByPlateforme(Long plateformeId) {
        return userRepository.findByPlateformeId(plateformeId)
                .stream()
                .map(u -> utilisateurMapper.toDto(u, projetRepository))
                .toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
        userRepository.deleteById(id);
    }

    public List<UserDto> getUsersByRoleAndPlateforme(UserRole role, Long plateformeId) {
        return userRepository.findByRoleAndPlateformeId(role, plateformeId).stream()
                .map(u -> utilisateurMapper.toDto(u, projetRepository))
                .toList();
    }

    public UserDto findById(Long id) {
        Utilisateur utilisateur = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'id " + id));
        return utilisateurMapper.toDto(utilisateur, projetRepository);
    }


    public SignupDto signUp(SignupDto data) {
        if (userRepository.existsByUsername(data.getUsername())) {
            throw new UsernameAlreadyExistsException(data.getUsername());
        }

        String encryptedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        Plateforme plateforme = (data.getPlateformeId() != null)
                ? plateformeService.findById(data.getPlateformeId())
                : null;

        Utilisateur user;

        switch (data.getRole()) {
            case PARRAIN:
                user = new Parrain();
                break;
            case PORTEUR:
                user = new Porteur();
                break;
            case STAFF:
                user = new Utilisateur();
                break;
            default:
                throw new IllegalArgumentException("Rôle inconnu : " + data.getRole());
        }

        user.setUsername(data.getUsername());
        user.setPassword(encryptedPassword);
        user.setEmail(data.getEmail());
        user.setRole(data.getRole());
        user.setFirstname(data.getFirstname());
        user.setLastname(data.getLastname());
        user.setEnabled(false);

        if (data.getRole() != UserRole.STAFF && data.getPlateformeId() != null) {
            user.setPlateforme(plateforme);
        }

        userRepository.save(user);

        return new SignupDto(
                user.getUsername(),
                null,
                user.getRole(),
                user.getEmail(),
                user.getPlateforme() != null ? user.getPlateforme().getPlateformeId() : null,
                user.getFirstname(),
                user.getLastname()
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
            if (dto.maxProjects() != null) {
                parrain.setMaxProjects(dto.maxProjects());
            }
            if (dto.isActive() != null) {
                parrain.setIsActive(dto.isActive());
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
            porteur.setRole(userEditDto.role());
        } else if (user instanceof Parrain parrain) {
            parrain.setParcours(userEditDto.parcours());
            parrain.setExpertise(userEditDto.expertise());
            parrain.setDeplacement(userEditDto.deplacement());
            parrain.setDisponibilite(userEditDto.disponibilite());
            parrain.setMaxProjects(userEditDto.maxProjects());
            parrain.setIsActive(true);
            parrain.setRole(userEditDto.role());
            parrain.setMaxProjects(userEditDto.maxProjects());
        } else {
            throw new IllegalStateException("Rôle inconnu.");
        }

        user.setEnabled(true);

        userRepository.save(user);
    }

    public void toggleParrainActive(Long parrainId) {
        Parrain parrain = (Parrain) userRepository.findById(parrainId).orElseThrow(EntityNotFoundException::new);
        parrain.setIsActive(!parrain.getIsActive());
        userRepository.save(parrain);
    }

    public List<UserDto> findParrainsDisponibles(Long plateformeId) {
        return userRepository.findParrainsActifsByPlateformeId(plateformeId)
                .stream()
                .map(u -> utilisateurMapper.toDto(u, projetRepository))
                .collect(Collectors.toList());
    }


    public Utilisateur getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Utilisateur userDetails) {
            return userRepository.findByUsername(userDetails.getUsername());
        } else {
            throw new RuntimeException("Utilisateur non authentifié");
        }
    }

    public boolean userCanEditOrDelete(Long userId) {
        Utilisateur currentUser = getCurrentUser();

        return currentUser.getAuthorities().contains(UserRole.ADMIN) ||
                currentUser.getAuthorities().contains(UserRole.STAFF) ||
                currentUser.getId().equals(userId);
    }

    public void verifierChargeParrain(Parrain parrain) {
        int nombreProjets = projetRepository.countByParrain(parrain);
        if (nombreProjets > parrain.getMaxProjects()) {
            parrain.setIsActive(false);
            userRepository.save(parrain);
            throw new IllegalStateException("Le Parrain a atteint le nombre maximum de projets affectés.");
        }
    }
}
