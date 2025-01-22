package org.simplon.TrouveTonMatch.dtos;

import lombok.Value;
import org.simplon.TrouveTonMatch.model.UserRole;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.Plateforme}
 */
@Value
public class PlateformeDto implements Serializable {
    Long id;
    String username;
    String email;
    UserRole role;
    AdresseDto adresse;
    String nom;
    String telephone;
}