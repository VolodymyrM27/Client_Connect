package com.motrechko.clientconnect.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.motrechko.clientconnect.dto.ReviewDto;
import com.motrechko.clientconnect.dto.UserTemplateHistoryDto;
import com.motrechko.clientconnect.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class BusinessStatisticResponse {
    private List<PopularRequirement> popularRequirement;
    private Double averageBusinessRatingByReviews;
    private List<UserTemplateHistoryDto> userVisitHistory;
    private List<ReviewDto> latestFeedback;
    private List<HourlyVisits> mostActiveVisitingHours;
    private List<UserVisits> topUsersByVisits;
    private VisitorStatistics visitorStatistics;
    private List<UserVisitsByDay> userVisitsByDays;
    private List<UserVisitsByMonth> userVisitsByMonths;
    private Double globalRating;
}
