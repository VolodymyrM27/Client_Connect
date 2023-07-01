package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.BusinessSupportedRequirement;
import com.motrechko.clientconnect.model.Requirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    @EntityGraph(attributePaths = {"category"})
    Business findByUser_Id(Long id);

    @Override
    @EntityGraph(attributePaths = {"category"})
    Optional<Business> findById(Long aLong);

    long deleteByIdAndBusinessSupportedRequirements(Long id, BusinessSupportedRequirement businessSupportedRequirements);
    @EntityGraph(attributePaths = {"category"})
    List<Business> findByCategory_Id(Long id);

    @Override
    @EntityGraph(attributePaths = {"category"})
    @NotNull
    List<Business> findAll();

    @Query("SELECT DISTINCT b FROM Business b JOIN FETCH b.businessSupportedRequirements br WHERE br.requirement IN :requirements")
    List<Business> findBySupportedRequirements(@Param("requirements") List<Requirement> requirements);
}