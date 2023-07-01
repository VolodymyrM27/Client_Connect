package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.BusinessSupportedRequirement;
import com.motrechko.clientconnect.model.PopularRequirement;
import com.motrechko.clientconnect.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BusinessSupportedRequirementRepository extends JpaRepository<BusinessSupportedRequirement, Integer> {

    @Modifying
    @Query("DELETE from BusinessSupportedRequirement bsr WHERE bsr.requirement.id = :requirementId AND bsr.business.id = :businessId")
    void deleteByBusinessIdAndRequirementId(@Param("businessId") Long businessId, @Param("requirementId") Long requirementId);


    List<BusinessSupportedRequirement> findByBusiness_Id(Long id);
    BusinessSupportedRequirement findByBusinessAndRequirement(Business business, Requirement requirement);



    @Query("SELECT new  com.motrechko.clientconnect.model.PopularRequirement(tr.requirement.requirementName, COUNT(tr) ) " +
            "FROM UserTemplateHistory uth " +
            "JOIN uth.template.templateRequirements tr " +
            "WHERE uth.business.id = :businessId " +
            "GROUP BY tr.requirement.requirementName " +
            "ORDER BY COUNT(tr) DESC")
    List<PopularRequirement> findMostPopularRequirements(@Param("businessId") Long businessId);


}