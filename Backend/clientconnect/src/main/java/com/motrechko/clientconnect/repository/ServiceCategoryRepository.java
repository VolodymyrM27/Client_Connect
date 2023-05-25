package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
}
