package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.TemplateRequirementDto;

import com.motrechko.clientconnect.model.TemplateRequirement;
import org.mapstruct.*;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING, uses = {RequirementMapper.class})
public interface TemplateRequirementMapper extends EntityMapper<TemplateRequirementDto, TemplateRequirement> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TemplateRequirement partialUpdate(TemplateRequirementDto templateRequirementDto, @MappingTarget TemplateRequirement templateRequirement);
}