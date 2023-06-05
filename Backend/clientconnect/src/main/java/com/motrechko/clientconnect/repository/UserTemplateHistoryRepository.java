package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTemplateHistoryRepository extends JpaRepository<UserTemplateHistory, Long> {
    Page<UserTemplateHistory> findFirst10ByBusiness_IdOrderByUsedAtDesc(Long id, Pageable pageable);

    List<UserTemplateHistory> findByUser_IdOrderByUsedAtDesc(Long id);


    @Query("SELECT new com.motrechko.clientconnect.model.HourlyVisits(HOUR(uth.usedAt), COUNT(*)) " +
            "FROM UserTemplateHistory uth " +
            "WHERE uth.business.id = :businessId " +
            "GROUP BY HOUR(uth.usedAt) " +
            "ORDER BY COUNT(*) DESC")
    List<HourlyVisits> getMostVisitedHoursRaw(@Param("businessId") Long businessId);


    @Query("SELECT new com.motrechko.clientconnect.model.UserVisits(uth.user.id, COUNT(*), up.firstName, up.lastName, up.dateOfBirth, up.gender) " +
            "FROM UserTemplateHistory uth " +
            "INNER JOIN UserProfile up ON uth.user.id = up.user.id " +
            "WHERE uth.business.id = :businessId " +
            "GROUP BY uth.user.id, up.firstName, up.lastName, up.dateOfBirth, up.gender " +
            "ORDER BY COUNT(*) DESC")
    List<UserVisits> getUsersByVisits(@Param("businessId") Long businessId);


    @Query(value = "SELECT  new com.motrechko.clientconnect.model.UserVisitsByDay( DATE(uth.usedAt), COUNT(*) ) " +
            "FROM UserTemplateHistory uth " +
            "WHERE uth.business.id = :businessId " +
            "GROUP BY DATE(uth.usedAt) " +
            "ORDER BY DATE(uth.usedAt)")
    List<UserVisitsByDay> findVisitsByDayRaw(@Param("businessId") Long businessId);


    @Query(value = "SELECT  new com.motrechko.clientconnect.model.UserVisitsByMonth( MONTH(uth.usedAt) ,  YEAR(uth.usedAt), COUNT(*) ) " +
            "FROM UserTemplateHistory uth " +
            "WHERE uth.business.id = :businessId " +
            "GROUP BY MONTH(uth.usedAt), YEAR(uth.usedAt) " +
            "ORDER BY YEAR(uth.usedAt), MONTH(uth.usedAt)")
    List<UserVisitsByMonth> findVisitsByMonthRaw(@Param("businessId") Long businessId);



}
