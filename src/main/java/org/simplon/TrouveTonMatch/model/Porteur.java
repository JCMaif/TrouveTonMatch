package org.simplon.TrouveTonMatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Porteur extends Utilisateur{

    private String disponibilite;

    @ManyToOne()
    @JoinColumn(name = "projet_id", nullable = true)
    private Projet projet;

    @ManyToOne()
    @JoinColumn(name = "plateforme_id")
    @JsonIgnore
    private Plateforme plateforme;
}
