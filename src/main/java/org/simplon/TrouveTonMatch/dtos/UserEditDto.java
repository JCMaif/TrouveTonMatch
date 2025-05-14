package org.simplon.TrouveTonMatch.dtos;

import org.simplon.TrouveTonMatch.model.UserRole;

public record UserEditDto(
        Long id,
        String password,
        Long addresseId,
        String disponibilite,
        String parcours,
        String expertise,
        String deplacement,
        String rue,
        String cpostal,
        String ville,
        Integer maxProjects,
        Boolean isActive,
        UserRole role
) {}
