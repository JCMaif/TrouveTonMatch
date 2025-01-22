package org.simplon.TrouveTonMatch.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.Adresse}
 */
@Value
public class AdresseDto implements Serializable {
    Long id;
    String rue;
    String cpostal;
    String ville;
}