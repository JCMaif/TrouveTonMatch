package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.simplon.TrouveTonMatch.dtos.ParrainDto;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.service.ProjetService;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParrainMapper {

    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    ParrainDto toDto(Parrain parrain);

}
