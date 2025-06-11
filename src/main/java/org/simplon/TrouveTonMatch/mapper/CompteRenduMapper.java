package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompteRenduMapper {
    CompteRendu toEntity(CompteRenduDto compteRenduDto);

    @Mapping(source = "porteur.id", target = "porteurId")
    CompteRenduDto toDto(CompteRendu compteRendu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompteRendu partialUpdate(CompteRenduDto compteRenduDto,
                              @MappingTarget CompteRendu compteRendu);
}