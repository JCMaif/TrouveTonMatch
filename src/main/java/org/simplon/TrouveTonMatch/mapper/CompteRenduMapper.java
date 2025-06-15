package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.dtos.CompteRenduDto;
import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Projet;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompteRenduMapper {

    @Mapping(target = "parrain", ignore = true)
    @Mapping(target = "projet", ignore = true)
    CompteRendu toEntity(CompteRenduDto compteRenduDto);

    @Mapping(source = "parrain.id", target = "parrainId")
    @Mapping(source = "projet.id", target = "projetId")
    @Mapping(source = "projet.porteur.id", target = "porteurId")
    @Mapping(source = "parrain.firstname", target = "parrainFirstname")
    @Mapping(source = "parrain.lastname", target = "ParrainLastname")
    @Mapping(source = "projet.porteur.firstname", target = "porteurFirstname")
    @Mapping(source = "projet.porteur.lastname", target = "PorteurLastname")
    @Mapping(source = "projet.title", target = "projetTitle")
    CompteRenduDto toDto(CompteRendu compteRendu);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompteRendu partialUpdate(@MappingTarget CompteRendu compteRendu, CompteRenduDto compteRenduDto);
}
