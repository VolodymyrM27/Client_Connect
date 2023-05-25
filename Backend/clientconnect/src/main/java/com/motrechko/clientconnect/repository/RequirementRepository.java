package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Requirement;
import com.motrechko.clientconnect.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RequirementRepository extends JpaRepository<Requirement, Long> {
    List<Requirement> findByCategory_Id(Long id);
    List<Requirement> findByCategory(ServiceCategory category);
}