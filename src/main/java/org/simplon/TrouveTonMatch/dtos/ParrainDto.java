package org.simplon.TrouveTonMatch.dtos;

public record ParrainDto(
    Long id,
    String firstname,
    String lastname,
    Boolean isActive,
    Integer nbrProjetsAffectes
) {
}
