package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.simplon.TrouveTonMatch.dtos.ProjetCreateDto;
import org.simplon.TrouveTonMatch.dtos.ProjetDto;
import org.simplon.TrouveTonMatch.dtos.ProjetUpdateDto;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ParrainMapper.class}
)
public interface ProjetMapper {

    @Mapping(target = "porteurId", source = "porteur.id")
    @Mapping(target = "porteurFirstname", source = "porteur.firstname")
    @Mapping(target = "porteurLastname", source = "porteur.lastname")
    @Mapping(target = "parrainId", source = "parrain.id")
    @Mapping(target = "parrainFirstname", source = "parrain.firstname")
    @Mapping(target = "parrainLastname", source = "parrain.lastname")
    ProjetDto toDto(Projet projet);

    ProjetCreateDto toCreateDto(Projet projet);

    default Projet toEntity(ProjetCreateDto dto, Porteur porteur) {
        if (dto == null) {
            return null;
        }

        return Projet.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .startingDate(dto.startingDate())
                .porteur(porteur)
                .build();
    }
    ProjetUpdateDto toUpdateDto(Projet save);
}
