package org.simplon.TrouveTonMatch.dtos;

import org.simplon.TrouveTonMatch.model.MoyenEchange;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompteRenduDto implements Serializable {
    private Long id;
    private LocalDate dateEchange;
    private LocalTime heureEchange;
    private MoyenEchange moyenEchange;
    private String sujets;
    private String resume;
    private String actionsAMener;
    private LocalDate prochainRdv;

    private Long parrainId;
    private Long projetId;
    private String projetTitle;

    private String porteurFirstname;
    private String porteurLastname;
    private String parrainFirstname;
    private String parrainLastname;
    private Long porteurId;
}