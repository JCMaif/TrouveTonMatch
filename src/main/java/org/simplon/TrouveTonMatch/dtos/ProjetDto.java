package org.simplon.TrouveTonMatch.dtos;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.Projet}
 */

public record ProjetDto(
    Long id,
    String title,
    String description,
    LocalDate startingDate,
    Long porteurId,
    String porteurFirstname,
    String porteurLastname,
    Long parrainId,
    String parrainFirstname,
    String parrainLastname
)
implements Serializable{}