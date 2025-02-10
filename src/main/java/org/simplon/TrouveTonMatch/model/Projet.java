package org.simplon.TrouveTonMatch.model;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "projet", cascade = CascadeType.ALL)
    private Porteur porteur;
}
