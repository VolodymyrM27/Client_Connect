package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.ReviewDto;
import com.motrechko.clientconnect.mapper.ReviewMapper;
import com.motrechko.clientconnect.model.Review;
import com.motrechko.clientconnect.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDto create(ReviewDto reviewDto){
        reviewDto.setReviewedAt(Instant.now());
        Review savedReview = reviewRepository.save(reviewMapper.toEntity(reviewDto));
        return reviewMapper.toDto(savedReview);
    }

    public List<ReviewDto> getAllReviewsByUserId(Long uderId) {
        return reviewMapper.toDto(reviewRepository.findByUser_Id(uderId));
    }

    public List<ReviewDto> getAllReviewsByBusiness(Long businessId) {
        return reviewMapper.toDto(reviewRepository.findByBusiness_Id(businessId));
    }
}
