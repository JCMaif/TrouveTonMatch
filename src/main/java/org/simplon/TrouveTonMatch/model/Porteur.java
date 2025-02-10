package org.simplon.TrouveTonMatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne()
    @JoinColumn(name = "projet_id", nullable = true)
    @JsonIgnore
    private Projet projet;

}
