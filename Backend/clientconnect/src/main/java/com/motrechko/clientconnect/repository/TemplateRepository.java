package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemplateRepository extends JpaRepository<Template, Long> {
}