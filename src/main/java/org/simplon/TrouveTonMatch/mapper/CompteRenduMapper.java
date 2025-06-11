package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompteRenduMapper {

    @Mapping(target = "porteur", ignore = true)
    @Mapping(target = "projet", ignore = true)
    CompteRendu toEntity(CompteRenduDto compteRenduDto);

    @Mapping(source = "porteur.id", target = "porteurId")
    @Mapping(source = "projet.id", target = "projetId")
    CompteRenduDto toDto(CompteRendu compteRendu);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompteRendu partialUpdate(@MappingTarget CompteRendu compteRendu, CompteRenduDto compteRenduDto);
}
