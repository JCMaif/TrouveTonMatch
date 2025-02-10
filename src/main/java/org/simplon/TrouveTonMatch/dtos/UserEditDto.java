package org.simplon.TrouveTonMatch.dtos;

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
        String ville
) {}
