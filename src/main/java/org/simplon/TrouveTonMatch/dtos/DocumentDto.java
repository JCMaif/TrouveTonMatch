package org.simplon.TrouveTonMatch.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.simplon.TrouveTonMatch.model.Document;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Document}
 */
public record DocumentDto(
        Long id,
        @NotNull
        String name,
        @NotNull String type,
        @NotNull String path,
        LocalDateTime uploadedAt,
        Long uploadedBy
)
        implements Serializable {
}