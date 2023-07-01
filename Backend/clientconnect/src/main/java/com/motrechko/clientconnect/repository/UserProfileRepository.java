package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.UserProfile;
import com.motrechko.clientconnect.model.VisitorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser_Id(Long id);


    @Query("SELECT up FROM UserProfile up WHERE up.user.id IN (SELECT DISTINCT uth.user.id FROM UserTemplateHistory uth WHERE uth.business.id = :businessId)")
    List<UserProfile> findDistinctVisitorsByBusiness(@Param("businessId") Long businessId);

    @Modifying
    @Query("DELETE from UserProfile up WHERE up.user.id = :employeeId")
    void deleteByEmployeeId(@Param("employeeId")Long employeeId);
}
