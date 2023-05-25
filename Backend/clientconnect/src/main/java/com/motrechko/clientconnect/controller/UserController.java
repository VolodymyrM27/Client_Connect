package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.UserDTO;
import com.motrechko.clientconnect.dto.UserProfileDTO;
import com.motrechko.clientconnect.service.UserProfileService;
import com.motrechko.clientconnect.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody  UserDTO userDTO){
        log.info("Updating user with id: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("Getting user with id: {}", id);
        return ResponseEntity.ok(userService.getUserDTO(id));
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
    public ResponseEntity<UserProfileDTO> updateUserProfileInfo(@PathVariable Long id, @RequestBody UserProfileDTO userProfileDTO){
        log.info("Updating info - profile for user with id: {}", id);
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileDTO));
    }
}
