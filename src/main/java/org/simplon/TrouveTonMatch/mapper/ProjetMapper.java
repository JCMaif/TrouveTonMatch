package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.simplon.TrouveTonMatch.dtos.ProjetCreateDto;
import org.simplon.TrouveTonMatch.dtos.ProjetDto;
import org.simplon.TrouveTonMatch.model.Projet;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjetMapper {

    ProjetDto toDto(Projet projet);

    ProjetCreateDto toCreateDto(Projet projet);

    default Projet toEntity(ProjetCreateDto dto) {
        if (dto == null) {
            return null;
        }

        Projet projet = Projet.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .startingDate(dto.startingDate())
                .build();

        return projet;
    }
}
