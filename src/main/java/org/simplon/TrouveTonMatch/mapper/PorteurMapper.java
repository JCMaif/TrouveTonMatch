package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.dtos.PorteurDto;
import org.simplon.TrouveTonMatch.model.Porteur;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PorteurMapper {
    Porteur toEntity(PorteurDto porteurDto);

    PorteurDto toDto(Porteur porteur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Porteur partialUpdate(PorteurDto porteurDto, @MappingTarget Porteur porteur);
}