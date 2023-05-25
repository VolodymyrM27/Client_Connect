package com.motrechko.clientconnect.mapper;


import com.motrechko.clientconnect.dto.TemplateDTO;
import com.motrechko.clientconnect.model.*;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TemplateRequirementMapper.class)
public interface TemplateMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "reviews", target = "reviewIds")
    @Mapping(source = "templateRequirements", target = "templateRequirements")
    @Mapping(source = "userTemplateHistories", target = "userTemplateHistoryIds")
    TemplateDTO toDto(Template template);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "templateRequirements", target = "templateRequirements")
    @Mapping(target = "reviews", source = "reviewIds")
    Template toEntity(TemplateDTO templateDto);

    Set<TemplateDTO> toDtoSet(Set<Template> templates);

    Set<Template> toEntitySet(Set<TemplateDTO> templateDtos);

    default <T> Set<Long> mapToIdSet(Set<T> entities, Function<T, Long> idMapper) {
        return Optional.ofNullable(entities)
                .orElse(Collections.emptySet())
                .stream()
                .map(idMapper)
                .collect(Collectors.toSet());
    }

    default <T> Set<T> mapToEntitySet(Set<Long> ids, Function<Long, T> entityBuilder) {
        return Optional.ofNullable(ids)
                .orElse(Collections.emptySet())
                .stream()
                .map(entityBuilder)
                .collect(Collectors.toSet());
    }

    default Set<Long> mapReviewSet(Set<Review> reviews) {
        return mapToIdSet(reviews, Review::getId);
    }

    default Set<Review> mapLongToReviewSet(Set<Long> reviews) {
        return mapToEntitySet(reviews, reviewId -> Review.builder().id(reviewId).build());
    }

    default Set<Long> mapUserTemplateHistorySet(Set<UserTemplateHistory> userTemplateHistories) {
        return mapToIdSet(userTemplateHistories, UserTemplateHistory::getId);
    }

    default Set<UserTemplateHistory> mapUserTemplateHistorySetToLong(Set<Long> userTemplateHistories) {
        return mapToEntitySet(userTemplateHistories, userTemplateHistoryId -> UserTemplateHistory.builder().id(userTemplateHistoryId).build());
    }
}
