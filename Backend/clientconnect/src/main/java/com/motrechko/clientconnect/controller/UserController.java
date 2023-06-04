package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.*;
import com.motrechko.clientconnect.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final TemplateService templateService;
    private final ReviewService reviewService;
    private final UserTemplateHistoryService userTemplateHistoryService;

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.info("Updating user with id: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("Getting user with id: {}", id);
        return ResponseEntity.ok(userService.getUserDTO(id));
    }

    @GetMapping("/{id}/templates")
    public ResponseEntity<Set<TemplateDTO>> getUserTemplates(@PathVariable Long id) {
        log.info("Getting user templates for user with id: {}", id);
        return ResponseEntity.ok(templateService.getTemplatesByUser(id));
    }

    @DeleteMapping("/{id}/templates/{templateId}")
    public ResponseEntity<Void> deleteUserTemplate(@PathVariable Long id, @PathVariable Long templateId) {
        log.info("Deleting template with id: {} for user with id: {}", templateId, id);
        templateService.deleteTemplateByUser(id, templateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/templates/trash")
    public ResponseEntity<Set<TemplateDTO>> getDeletedTemplates(@PathVariable Long id) {
        log.info("Getting deleted templates for user with id: {}", id);
        return ResponseEntity.ok(templateService.getDeletedTemplatesByUser(id));
    }


    @PostMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> createUserProfileInfo(@PathVariable Long id, @RequestBody @Valid UserProfileDTO profile) {
        log.info("Creating info - profile for user with id: {}", id);
        return new ResponseEntity<>(userProfileService.createProfile(id, profile), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> getUserProfileInfo(@PathVariable Long id) {
        log.info("Getting info - profile for user with id: {}", id);
        return ResponseEntity.ok(userProfileService.getUserProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> updateUserProfileInfo(@PathVariable Long id, @RequestBody UserProfileDTO userProfileDTO) {
        log.info("Updating info - profile for user with id: {}", id);
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileDTO));
    }
    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<ReviewDto>> getAllReviews(@PathVariable Long userId){
        return ResponseEntity.ok(reviewService.getAllReviewsByUserId(userId));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<UserTemplateHistoryDto>> getUserHistoryByDate(@PathVariable Long userId){
        return ResponseEntity.ok(userTemplateHistoryService.getUserTemplateHistoryByDate(userId));
    }
}
