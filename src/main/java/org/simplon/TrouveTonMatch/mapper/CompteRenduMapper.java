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

    default CompteRenduDto toDto(CompteRendu cr) {
        if (cr == null) return null;

        CompteRenduDto.CompteRenduDtoBuilder builder = CompteRenduDto.builder()
                .id(cr.getId())
                .dateEchange(cr.getDateEchange())
                .heureEchange(cr.getHeureEchange())
                .moyenEchange(cr.getMoyenEchange())
                .sujets(cr.getSujets())
                .resume(cr.getResume())
                .actionsAMener(cr.getActionsAMener())
                .prochainRdv(cr.getProchainRdv());

        if (cr.getParrain() != null) {
            builder.parrainId(cr.getParrain().getId())
                    .parrainFirstname(cr.getParrain().getFirstname())
                    .parrainLastname(cr.getParrain().getLastname());
        }

        if (cr.getProjet() != null) {
            builder.projetId(cr.getProjet().getId())
                    .projetTitle(cr.getProjet().getTitle());

            if (cr.getProjet().getPorteur() != null) {
                builder.porteurId(cr.getProjet().getPorteur().getId())
                        .porteurFirstname(cr.getProjet().getPorteur().getFirstname())
                        .porteurLastname(cr.getProjet().getPorteur().getLastname());
            }
        }

        return builder.build();
    }

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompteRendu partialUpdate(@MappingTarget CompteRendu compteRendu, CompteRenduDto compteRenduDto);
}

