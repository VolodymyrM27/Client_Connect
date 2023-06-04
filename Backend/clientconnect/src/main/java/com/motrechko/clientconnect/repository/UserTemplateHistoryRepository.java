package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.UserTemplateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTemplateHistoryRepository extends JpaRepository<UserTemplateHistory, Long> {
    List<UserTemplateHistory> findByUser_IdOrderByUsedAtDesc(Long id);
}