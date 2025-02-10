package org.simplon.TrouveTonMatch.dtos;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProjetCreateDto(
        Long id,
        String title,
        String description,
        LocalDate startingDate,
        Long porteurId
) {
}
