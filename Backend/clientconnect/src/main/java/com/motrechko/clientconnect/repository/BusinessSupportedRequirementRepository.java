package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.BusinessSupportedRequirement;
import com.motrechko.clientconnect.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessSupportedRequirementRepository extends JpaRepository<BusinessSupportedRequirement, Integer> {
    BusinessSupportedRequirement findByBusinessAndRequirement(Business business, Requirement requirement);

}