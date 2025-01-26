package org.simplon.TrouveTonMatch.service;

import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.UserRole;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PlateformeService plateformeService;

    public UserService(UserRepository userRepository, PlateformeService plateformeService) {
        this.userRepository = userRepository;
        this.plateformeService = plateformeService;
    }

    public List<UserDto> getAllUsers() {
        List<Utilisateur> users = userRepository.findAll();
        Map<Long, String> plateformeMap = plateformeService.getPlaterformeNameById();

        return users.stream().map(UserDto::fromEntity).toList();
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

    public UserDto findById(Long id) {
        return UserDto.fromEntity(userRepository.findById(id).orElseThrow());
    }

    public UserDto updateProfile(String username, UserDto userDto) {
        Utilisateur user = userRepository.findByUsername(username);
        user.setUsername(userDto.getUsername() != null ? userDto.getUsername() : user.getUsername());
        user.setRole(userDto.getRole() != null ? userDto.getRole() : user.getRole());
        user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail());
        user.setEnabled(userDto.getEnabled() != null ? userDto.getEnabled() : user.isEnabled());
        user.setAdresse(userDto.getAdresse() != null ? userDto.getAdresse() : user.getAdresse());
        if(user instanceof Porteur) {
            ((Porteur) user).setPlateforme(userDto.getPlateformeId() != null
                    ? plateformeService.findById(userDto.getPlateformeId())
                    : ((Porteur) user).getPlateforme());
        } else if (user instanceof Parrain) {
            ((Parrain) user).setPlateforme(userDto.getPlateformeId() != null
                    ? plateformeService.findById(userDto.getPlateformeId())
                    : ((Parrain) user).getPlateforme());
        }
        return UserDto.fromEntity(userRepository.save(user));
    }
}
