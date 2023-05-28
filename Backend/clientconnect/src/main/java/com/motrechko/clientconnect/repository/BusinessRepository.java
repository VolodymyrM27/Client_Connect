package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}