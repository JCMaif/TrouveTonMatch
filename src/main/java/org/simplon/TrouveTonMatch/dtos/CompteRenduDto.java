package org.simplon.TrouveTonMatch.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.MoyenEchange;
import org.simplon.TrouveTonMatch.model.Porteur;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link CompteRendu}
 */
public record CompteRenduDto(
        Long id,
        @NotNull LocalDate dateEchange,
        LocalTime heureEchange,
        MoyenEchange moyenEchange,
        String sujets,
        String resume,
        String actionsAMener,
        LocalDate prochainRdv,
        Long porteurId
)
        implements Serializable {
}