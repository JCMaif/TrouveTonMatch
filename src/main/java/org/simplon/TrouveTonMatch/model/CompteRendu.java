package org.simplon.TrouveTonMatch.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "compte_rendu")
public class CompteRendu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEchange;
    private LocalTime heureEchange;

    @Enumerated(EnumType.STRING)
    private MoyenEchange moyenEchange;

    private String sujets;
    @Column(length = 2000)
    private String resume;

    @Column(length = 2000)
    private String actionsAMener;

    private LocalDate prochainRdv;

    @ManyToOne
    @JsonIgnore
    private Parrain parrain;

    @ManyToOne
    @JsonIgnore
    private Projet projet;
}
