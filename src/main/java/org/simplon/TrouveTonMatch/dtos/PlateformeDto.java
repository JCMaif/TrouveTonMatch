package org.simplon.TrouveTonMatch.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.Plateforme}
 */
@Value
public class PlateformeDto implements Serializable {
    Long id;
    String email;
    AdresseDto adresse;
    String nom;
    String telephone;
}