package org.simplon.TrouveTonMatch.dtos;

import lombok.Value;
import org.simplon.TrouveTonMatch.model.Adresse;
import org.simplon.TrouveTonMatch.model.UserRole;

import java.io.Serializable;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.Parrain}
 */
@Value
public class ParrainDto implements Serializable {
    Long id;
    String username;
    String email;
    UserRole role;
    Adresse adresse;
    String parcours;
    String expertise;
    String deplacements;
    String disponibilites;
}