package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.simplon.TrouveTonMatch.dtos.DocumentDto;
import org.simplon.TrouveTonMatch.model.Document;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {
    Document toEntity(DocumentDto documentDto);

    DocumentDto toDto(Document document);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Document partialUpdate(DocumentDto documentDto,
            @MappingTarget Document document);
}