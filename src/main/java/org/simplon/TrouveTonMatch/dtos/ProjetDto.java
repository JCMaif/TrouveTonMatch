package org.simplon.TrouveTonMatch.dtos;

import lombok.Builder;
import lombok.Value;
import org.simplon.TrouveTonMatch.model.Porteur;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.Projet}
 */
@Value
@Builder
public class ProjetDto implements Serializable {
    Long id;
    LocalDate startingDate;
    String title;
    String description;
    Porteur porteur;
    Long plateformeId;
}