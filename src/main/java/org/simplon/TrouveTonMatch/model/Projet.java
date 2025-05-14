package org.simplon.TrouveTonMatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startingDate;
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "porteur_id", nullable = false)
    @JsonIgnore
    private Porteur porteur;

    @ManyToOne
    @JoinColumn(name = "parrain_id")
    @JsonIgnore
    private Parrain parrain;
}
