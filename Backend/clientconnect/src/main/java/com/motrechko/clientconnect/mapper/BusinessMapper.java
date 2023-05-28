package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.BusinessDto;
import com.motrechko.clientconnect.model.Business;
import org.mapstruct.*;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessMapper extends EntityMapper<BusinessDto, Business> {

    @Override
    @Mapping(source = "userId", target = "user.id")
    Business toEntity(BusinessDto dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    BusinessDto toDto(Business entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Business partialUpdate(BusinessDto businessDto, @MappingTarget Business business);
}