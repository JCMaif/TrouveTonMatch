package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.dtos.ParrainDto;
import org.simplon.TrouveTonMatch.model.Parrain;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParrainMapper {
    Parrain toEntity(ParrainDto parrainDto);

    ParrainDto toDto(Parrain parrain);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Parrain partialUpdate(ParrainDto parrainDto, @MappingTarget Parrain parrain);
}