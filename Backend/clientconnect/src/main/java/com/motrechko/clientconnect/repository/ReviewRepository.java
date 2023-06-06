package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    long countByBusiness_Id(Long id);
    List<Review> findByBusiness_Id(Long id);

    Page<Review> findFirst10ByBusiness_IdOrderByReviewedAtDesc(Long id, Pageable pageable);
    List<Review> findByUser_Id(Long id);

    @Query(value = "SELECT AVG(r.rating) FROM reviews r WHERE r.business_id = :businessId", nativeQuery = true)
    Double findAverageBusinessRating(@Param("businessId") Long businessId);
}