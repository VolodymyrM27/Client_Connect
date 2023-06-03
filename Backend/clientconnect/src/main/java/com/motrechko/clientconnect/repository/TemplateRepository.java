package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.ServiceCategory;
import com.motrechko.clientconnect.model.Status;
import com.motrechko.clientconnect.model.Template;
import com.motrechko.clientconnect.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface TemplateRepository extends JpaRepository<Template, Long> {
    @EntityGraph(value = "template-with-all-fields")
    Optional<Template> findByUserAndCategoryAndStatus(User user, ServiceCategory category, Status status);
    Set<Template> findByUser_IdAndStatus(Long id, Status status);

    Optional<Template> findByIdAndStatusNot(Long id, Status status);

    Set<Template> findByUser_IdAndStatusNot(Long id, Status status);

}