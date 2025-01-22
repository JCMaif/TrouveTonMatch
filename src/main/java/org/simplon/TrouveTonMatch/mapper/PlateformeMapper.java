package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.dtos.PlateformeDto;
import org.simplon.TrouveTonMatch.model.Plateforme;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlateformeMapper {
    Plateforme toEntity(PlateformeDto plateformeDto);

    PlateformeDto toDto(Plateforme plateforme);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Plateforme partialUpdate(PlateformeDto plateformeDto, @MappingTarget Plateforme plateforme);
}