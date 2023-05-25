package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface TemplateRepository extends JpaRepository<Template, Long> {
    Set<Template> findByUser_Id(Long id);
}