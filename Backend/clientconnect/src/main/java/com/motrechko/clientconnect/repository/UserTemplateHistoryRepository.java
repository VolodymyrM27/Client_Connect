package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.UserTemplateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTemplateHistoryRepository extends JpaRepository<UserTemplateHistory, Long> {
}