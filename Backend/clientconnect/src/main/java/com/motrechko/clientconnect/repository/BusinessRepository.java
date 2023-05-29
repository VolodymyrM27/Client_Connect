package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Business;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    @EntityGraph(attributePaths = {"category"})
    List<Business> findByCategory_Id(Long id);

    @Override
    @EntityGraph(attributePaths = {"category"})
    @NotNull
    List<Business> findAll();
}