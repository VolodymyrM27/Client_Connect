package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.ReviewDto;
import com.motrechko.clientconnect.model.Review;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper extends EntityMapper<ReviewDto, Review>{

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "templateId", target = "template.id")
    @Mapping(source = "businessId", target = "business.id")
    @Override
    Review toEntity(ReviewDto dto);

    @InheritInverseConfiguration(name = "toEntity")
    @Override
    ReviewDto toDto(Review entity);
}
