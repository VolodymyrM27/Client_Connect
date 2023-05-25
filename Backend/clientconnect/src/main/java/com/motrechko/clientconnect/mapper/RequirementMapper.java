package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.RequirementDto;
import com.motrechko.clientconnect.model.Requirement;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequirementMapper extends EntityMapper<RequirementDto, Requirement> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Requirement partialUpdate(RequirementDto requirementDto, @MappingTarget Requirement requirement);
}