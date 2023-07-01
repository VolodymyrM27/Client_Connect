package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.*;
import com.motrechko.clientconnect.payload.BusinessStatisticResponse;
import com.motrechko.clientconnect.payload.EmployeeRequest;
import com.motrechko.clientconnect.payload.EmployeeResponse;
import com.motrechko.clientconnect.payload.RegisterRequest;
import com.motrechko.clientconnect.service.BusinessService;
import com.motrechko.clientconnect.service.BusinessStatisticService;
import com.motrechko.clientconnect.service.ReviewService;
import com.motrechko.clientconnect.service.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/business")
public class BusinessController {

    private final BusinessService businessService;
    private final TerminalService terminalService;
    private final ReviewService reviewService;
    private final BusinessStatisticService businessStatisticService;

    @GetMapping
    public ResponseEntity<List<BusinessDto>> getAllBusiness() {
        return ResponseEntity.ok(businessService.getAllBusiness());
    }
    @GetMapping("/{businessId}")
    public ResponseEntity<BusinessDto> getAllBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(businessService.getBusinessById(businessId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<BusinessDto> getBusinessByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(businessService.getBusinessByUser(userId));
    }


    @GetMapping("/category/{idCategory}")
    public ResponseEntity<List<BusinessDto>> getAllBusinessByCategory(@PathVariable Long idCategory) {
        return ResponseEntity.ok(businessService.getAllBusinessByCategory(idCategory));
    }


    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody BusinessDto businessDto) {
        return ResponseEntity.ok(businessService.create(businessDto));
    }

    @PostMapping("/{businessId}/requirements")
    public ResponseEntity<Set<RequirementDto>> addRequirements(@PathVariable Long businessId, @RequestBody Set<RequirementDto> requirementDtos) {
        return ResponseEntity.ok(businessService.addRequirements(businessId, requirementDtos));
    }

    @DeleteMapping("/{businessId}/requirements/{requirementId}")
    public ResponseEntity<Void> deleteRequirement(@PathVariable Long businessId, @PathVariable Long requirementId) {
        businessService.deleteRequirement(businessId, requirementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{businessId}/requirements")
    public ResponseEntity<List<RequirementDto>> getSupportedRequirements(@PathVariable Long businessId) {
        return ResponseEntity.ok(businessService.getSupportedRequirements(businessId));
    }

    @PostMapping("/terminals")
    public ResponseEntity<TerminalDto> addNewTerminals(@RequestBody TerminalDto terminalDto) {
        return ResponseEntity.ok(terminalService.create(terminalDto));
    }

    @GetMapping("/{businessId}/terminals")
    public ResponseEntity<List<TerminalDto>> getAllBusinessTerminal(@PathVariable Long businessId) {
        return ResponseEntity.ok(terminalService.getAllBusinessTermainals(businessId));
    }

    @DeleteMapping("/{businessId}/terminals/{termainalId}")
    public ResponseEntity<Void> getAllBusinessTerminal(@PathVariable Long businessId, @PathVariable Long termainalId) {
        terminalService.deleteTerminal(businessId, termainalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{businessId}/reviews")
    public ResponseEntity<List<ReviewDto>> getAllBusinessReviews(@PathVariable Long businessId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByBusiness(businessId));
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<BusinessStatisticResponse> getBusinessStatistic(@PathVariable Long id) {
        return ResponseEntity.ok(businessStatisticService.getStatistic(id));
    }

    @PostMapping("/{businessId}/employee")
    public ResponseEntity<BusinessUserProfileDto> createNewEmployee(@RequestBody EmployeeRequest employeeRequest,
                                                                    @PathVariable Long businessId) {
        return ResponseEntity.ok(businessService.createNewEmployee(employeeRequest.getRegisterRequest(),
                employeeRequest.getUserProfileDTO(),
                businessId));
    }

    @GetMapping("/{businessId}/employee")
    public ResponseEntity<List<EmployeeResponse>> getAllBusinessEmployees(@PathVariable Long businessId){
        return  ResponseEntity.ok(businessService.getAllBusinessEmployees(businessId));
    }

    @DeleteMapping("/{businessId}/employee/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId,@PathVariable Long businessId ) {
        businessService.deleteEmployee(businessId, employeeId);
        return ResponseEntity.noContent().build();
    }

}
