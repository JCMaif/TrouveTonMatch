package org.simplon.TrouveTonMatch.dtos;

import org.simplon.TrouveTonMatch.model.*;

public record UserDto(
        String username,
        UserRole role,
        Long id,
        Long plateformeId,
        String plateformeName,
        String email,
        Boolean enabled,
        Adresse adresse,
        String parcours,
        String expertise,
        String deplacement,
        String disponibilite,
        String projetTitle,
        Long projetId
) {
}
