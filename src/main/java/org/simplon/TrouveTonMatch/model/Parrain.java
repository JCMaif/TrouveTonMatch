package org.simplon.TrouveTonMatch.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parrain extends Utilisateur{

    private String parcours;
    private String expertise;
    private String deplacements;
    private String disponibilites;

}
