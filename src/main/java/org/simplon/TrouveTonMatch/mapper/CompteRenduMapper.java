package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompteRenduMapper {
    CompteRendu toEntity(CompteRenduDto compteRenduDto);

    CompteRenduDto toDto(CompteRendu compteRendu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompteRendu partialUpdate(CompteRenduDto compteRenduDto,
            @MappingTarget CompteRendu compteRendu);
}