package org.simplon.TrouveTonMatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parrain")
public class Parrain extends Utilisateur{

    private String parcours;
    private String expertise;
    private String deplacements;
    private String disponibilites;
}
