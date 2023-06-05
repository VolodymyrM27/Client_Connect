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
        businessStatisticResponse.setAverageBusinessRatingByReviews(getAverageBusinessRatingByReviews(id));
        businessStatisticResponse.setUserVisitHistory(getUserHistoryByDate(id));
        businessStatisticResponse.setLatestFeedback(latestReviews(id));
        businessStatisticResponse.setMostActiveVisitingHours(getMostVisitedHours(id));
        businessStatisticResponse.setTopUsersByVisits(getTopUsersByVisit(id));
        businessStatisticResponse.setVisitorStatistics(getVisitorStatistic(id));
        businessStatisticResponse.setUserVisitsByDays(getUserVisitsByDay(id));
        businessStatisticResponse.setUserVisitsByMonths(getUserVisitsByMonth(id));


        return businessStatisticResponse;
    }

    private List<PopularRequirement> getPopularRequirementByBusiness(Long id) {
        return businessSupportedRequirementRepository.findMostPopularRequirements(id);
    }

    private Double getAverageBusinessRatingByReviews(Long id) {
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

}
