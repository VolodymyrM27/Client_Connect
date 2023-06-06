package com.motrechko.clientconnect.service;


import com.motrechko.clientconnect.dto.ReviewDto;
import com.motrechko.clientconnect.dto.UserTemplateHistoryDto;
import com.motrechko.clientconnect.model.*;
import com.motrechko.clientconnect.payload.BusinessStatisticResponse;
import com.motrechko.clientconnect.repository.BusinessSupportedRequirementRepository;
import com.motrechko.clientconnect.repository.ReviewRepository;
import com.motrechko.clientconnect.repository.UserProfileRepository;
import com.motrechko.clientconnect.repository.UserTemplateHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessStatisticService {

    private final BusinessSupportedRequirementRepository businessSupportedRequirementRepository;
    private final ReviewRepository reviewRepository;
    private final UserTemplateHistoryService userTemplateHistoryService;
    private final UserTemplateHistoryRepository userTemplateHistoryRepository;
    private final ReviewService reviewService;
    private final UserProfileRepository userProfileRepository;

    public BusinessStatisticResponse getStatistic(Long id) {
        BusinessStatisticResponse businessStatisticResponse = new BusinessStatisticResponse();
        businessStatisticResponse.setPopularRequirement(getPopularRequirementByBusiness(id));
        businessStatisticResponse.setAverageBusinessRatingByReviews(calculateAverageBusinessRating(id));
        businessStatisticResponse.setUserVisitHistory(getUserHistoryByDate(id));
        businessStatisticResponse.setLatestFeedback(latestReviews(id));
        businessStatisticResponse.setMostActiveVisitingHours(getMostVisitedHours(id));
        businessStatisticResponse.setTopUsersByVisits(getTopUsersByVisit(id));
        businessStatisticResponse.setVisitorStatistics(getVisitorStatistic(id));
        businessStatisticResponse.setUserVisitsByDays(getUserVisitsByDay(id));
        businessStatisticResponse.setUserVisitsByMonths(getUserVisitsByMonth(id));

        UserVisitsByMonth lastMonth = UserVisitsByMonth.builder().month(0).visits(0).build();

        if (!businessStatisticResponse.getUserVisitsByMonths().isEmpty()) {
            lastMonth = businessStatisticResponse.getUserVisitsByMonths().get(businessStatisticResponse.getUserVisitsByMonths().size() - 1);
        }

        businessStatisticResponse.setGlobalRating(
                getGlobalRating(id, businessStatisticResponse.getAverageBusinessRatingByReviews(), lastMonth)
        );

        return businessStatisticResponse;
    }

    private List<PopularRequirement> getPopularRequirementByBusiness(Long id) {
        return businessSupportedRequirementRepository.findMostPopularRequirements(id);
    }

    private Double calculateAverageBusinessRating(Long id) {
        return reviewRepository.findAverageBusinessRating(id);
    }

    private List<UserTemplateHistoryDto> getUserHistoryByDate(Long id) {
        return userTemplateHistoryService.getFirst10HistoriesByBusinessIdOrderByUsedAtDesc(id);
    }

    private List<ReviewDto> latestReviews(Long id) {
        return reviewService.getLast10ReviewsByBusiness(id);
    }

    private List<HourlyVisits> getMostVisitedHours(Long id) {

        return userTemplateHistoryRepository.getMostVisitedHoursRaw(id);
    }

    private List<UserVisits> getTopUsersByVisit(Long id) {
        return userTemplateHistoryRepository.getUsersByVisits(id);
    }

    private VisitorStatistics getVisitorStatistic(Long id){
        List<UserProfile> userProfiles = userProfileRepository.findDistinctVisitorsByBusiness(id);

        double averageAge = userProfiles.stream()
                .mapToInt(profile -> Period.between(profile.getDateOfBirth(), LocalDate.now()).getYears())
                .average()
                .orElse(0);

        long maleCount = userProfiles.stream()
                .filter(profile -> "Male".equals(profile.getGender()))
                .count();

        long femaleCount = userProfiles.stream()
                .filter(profile -> "Female".equals(profile.getGender()))
                .count();

        return VisitorStatistics.builder()
                .averageAge(averageAge)
                .maleUsers(maleCount)
                .femaleUsers(femaleCount)
                .build();

    }

    private List<UserVisitsByDay> getUserVisitsByDay(Long id){
        return userTemplateHistoryRepository.findVisitsByDayRaw(id);
    }

    private List<UserVisitsByMonth> getUserVisitsByMonth(Long id){
        return userTemplateHistoryRepository.findVisitsByMonthRaw(id);
    }

    private double getGlobalRating(Long id, Double averageRatingByReviews, UserVisitsByMonth userVisitsByMonth){
        double averageRating = averageRatingByReviews;
        double reviewCount = reviewRepository.countByBusiness_Id(id);
        double visitsPerMonth = userVisitsByMonth.getVisits();
        int uniqUsers = userTemplateHistoryRepository.findFrequentVisitors(id);
        final double RATING_WEIGHT = 3.0;
        final double REVIEW_WEIGHT = 1.0;
        final double VISIT_WEIGHT = 2.0;
        final double REPEAT_WEIGHT = 2.0;
        return (averageRating * RATING_WEIGHT + reviewCount * REVIEW_WEIGHT + visitsPerMonth * VISIT_WEIGHT + uniqUsers * REPEAT_WEIGHT)
                / (RATING_WEIGHT + REPEAT_WEIGHT + VISIT_WEIGHT + REPEAT_WEIGHT);
    }

}
