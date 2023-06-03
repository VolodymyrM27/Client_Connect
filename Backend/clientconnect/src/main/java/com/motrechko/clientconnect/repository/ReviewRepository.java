package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBusiness_Id(Long id);
    List<Review> findByUser_Id(Long id);
}