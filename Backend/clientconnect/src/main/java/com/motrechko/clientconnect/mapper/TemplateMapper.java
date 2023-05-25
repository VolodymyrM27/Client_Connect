package com.motrechko.clientconnect.mapper;


import com.motrechko.clientconnect.dto.TemplateDTO;
import com.motrechko.clientconnect.model.*;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TemplateRequirementMapper.class)
public interface TemplateMapper{
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "reviews", target = "reviewIds")
    @Mapping(source = "templateRequirements", target = "templateRequirements")
    @Mapping(source = "userTemplateHistories", target = "userTemplateHistoryIds")
    TemplateDTO toDto(Template template);
    default Set<Long> mapReviewSet(Set<Review> reviews) {
        return reviews.stream()
                .map(Review::getId)
                .collect(Collectors.toSet());
    }


    default Set<Long> mapUserTemplateHistorySet(Set<UserTemplateHistory> userTemplateHistories) {
        return userTemplateHistories.stream()
                .map(UserTemplateHistory::getId)
                .collect(Collectors.toSet());
    }
}
