package org.simplon.TrouveTonMatch.dtos;

import org.simplon.TrouveTonMatch.model.MoyenEchange;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link org.simplon.TrouveTonMatch.model.CompteRendu}
 */
public record CompteRenduDto(
        Long id,
        LocalDate dateEchange,
        LocalTime heureEchange,
        MoyenEchange moyenEchange,
        String sujets,
        String resume,
        String actionsAMener,
        LocalDate prochainRdv,
        Long parrainId,
        Long projetId,
        String projetTitle,
        String porteurFirstname,
        String PorteurLastname,
        String parrainFirstname,
        String ParrainLastname,
        Long porteurId
)
        implements Serializable {
}