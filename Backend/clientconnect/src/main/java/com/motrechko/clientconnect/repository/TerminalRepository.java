package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {
    List<Terminal> findByBusiness_Id(Long id);
    Optional<Terminal> findByUuid(String uuid);

    @Modifying
    @Query("DELETE from Terminal tp WHERE tp.business.id = :businessId AND tp.id = :terminaltId")
    void deleteByBusinessIdAndTerminalId(@Param("businessId") Long businessId, @Param("terminaltId") Long terminaltId);


}