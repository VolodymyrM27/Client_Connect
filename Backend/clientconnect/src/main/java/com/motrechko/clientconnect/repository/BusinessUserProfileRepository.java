package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.BusinessUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessUserProfileRepository extends JpaRepository<BusinessUserProfile, Long> {
    List<BusinessUserProfile> findByBusiness_Id(Long id);

    @Modifying
    @Query("DELETE from BusinessUserProfile bup WHERE bup.business.id = :businessId AND bup.user.id = :employeeId")
    void deleteByBusinessIdAndEmployeeId(@Param("businessId") Long businessId, @Param("employeeId") Long employeeId);
}