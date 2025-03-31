package org.simplon.TrouveTonMatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.el.parser.BooleanNode;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parrain extends Utilisateur{

    private String parcours;
    private String expertise;
    private String deplacement;
    private String disponibilite;

    private Integer maxProjects;
    private Boolean isActive = true;

    @OneToMany(mappedBy = "parrain")
    private List<Projet> projetsSuivis;
}
