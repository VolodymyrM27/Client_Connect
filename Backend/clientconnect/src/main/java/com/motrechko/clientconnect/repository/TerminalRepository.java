package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {
    Optional<Terminal> findByUuid(String uuid);
}