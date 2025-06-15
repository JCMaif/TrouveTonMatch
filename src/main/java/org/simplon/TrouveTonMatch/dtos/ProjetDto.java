package org.simplon.TrouveTonMatch.dtos;

import lombok.Builder;
import lombok.Value;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;

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
    String PorteurLastname,
    Long parrainId,
    String parrainFirstname,
    String ParrainLastname
)
implements Serializable{}