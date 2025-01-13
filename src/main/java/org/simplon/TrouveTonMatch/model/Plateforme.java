package org.simplon.TrouveTonMatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plateforme extends Utilisateur{

    private String nom;
    private String telephone;

    @OneToMany(mappedBy = "plateforme", cascade = CascadeType.ALL)
    private List<Parrain> parrains;

    @OneToMany(mappedBy = "plateforme", cascade = CascadeType.ALL)
    private List<Porteur> porteurs;
}
