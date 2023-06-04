package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.UserTemplateHistoryDto;
import com.motrechko.clientconnect.model.UserTemplateHistory;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserTemplateHistoryMapper extends EntityMapper<UserTemplateHistoryDto, UserTemplateHistory> {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "templateId", target = "template.id")
    @Mapping(source = "businessId", target = "business.id")
    @Mapping(source = "businessBusinessName", target = "business.businessName")
    @Override
    UserTemplateHistory toEntity(UserTemplateHistoryDto dto);

    @InheritInverseConfiguration(name = "toEntity")
    @Override
    UserTemplateHistoryDto toDto(UserTemplateHistory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserTemplateHistory partialUpdate(UserTemplateHistoryDto userTemplateHistoryDto, @MappingTarget UserTemplateHistory userTemplateHistory);
}