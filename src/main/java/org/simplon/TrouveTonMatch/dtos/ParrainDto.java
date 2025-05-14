package org.simplon.TrouveTonMatch.dtos;

public record ParrainDto(
    Long id,
    String firstName,
    String lastName,
    Boolean isActive,
    Integer nbrProjetsAffectes
) {
}
