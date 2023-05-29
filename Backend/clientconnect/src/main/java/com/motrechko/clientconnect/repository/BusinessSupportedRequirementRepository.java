package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.dto.RequirementDto;
import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.BusinessSupportedRequirement;
import com.motrechko.clientconnect.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BusinessSupportedRequirementRepository extends JpaRepository<BusinessSupportedRequirement, Integer> {
    Set<BusinessSupportedRequirement> findByBusiness_Id(Long id);
    BusinessSupportedRequirement findByBusinessAndRequirement(Business business, Requirement requirement);
}