package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.TemplateRequirementDto;

import com.motrechko.clientconnect.model.TemplateRequirement;
import org.mapstruct.*;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateRequirementMapper extends EntityMapper<TemplateRequirementDto, TemplateRequirement> {

    @Mapping(source = "requirement.requirementName", target = "requirementName")
    @Mapping(source = "requirement.id", target = "requirementId")
    @Override
    TemplateRequirementDto toDto(TemplateRequirement entity);

    @Mapping(source = "requirementName", target = "requirement.requirementName")
    @Mapping(source = "requirementId", target = "requirement.id")
    @Override
    TemplateRequirement toEntity(TemplateRequirementDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TemplateRequirement partialUpdate(TemplateRequirementDto templateRequirementDto, @MappingTarget TemplateRequirement templateRequirement);
}