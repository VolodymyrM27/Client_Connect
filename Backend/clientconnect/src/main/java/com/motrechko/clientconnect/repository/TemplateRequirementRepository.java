package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.TemplateRequirement;
import com.motrechko.clientconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TemplateRequirementRepository extends JpaRepository<TemplateRequirement, Long> {
    List<TemplateRequirement> findByTemplate_IdAndTemplate_User(Long id, User user);
    Set<TemplateRequirement> findByTemplate_User_Id(Long id);
}