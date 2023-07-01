package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.BusinessUserProfileDto;
import com.motrechko.clientconnect.dto.TerminalDto;
import com.motrechko.clientconnect.model.BusinessUserProfile;
import com.motrechko.clientconnect.model.Terminal;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessUserProfileMapper extends EntityMapper<BusinessUserProfileDto, BusinessUserProfile> {
    @Override
    @Mapping(source = "businessId", target = "business.id")
    @Mapping(source = "userId", target = "user.id")
    BusinessUserProfile toEntity(BusinessUserProfileDto dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    BusinessUserProfileDto toDto(BusinessUserProfile entity);
}