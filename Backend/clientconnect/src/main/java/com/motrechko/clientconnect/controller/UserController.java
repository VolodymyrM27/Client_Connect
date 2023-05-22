package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.UserDTO;
import com.motrechko.clientconnect.dto.UserProfileDTO;
import com.motrechko.clientconnect.service.UserProfileService;
import com.motrechko.clientconnect.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody  UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDTO(id));
    }

    @PostMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> createUserProfile(@PathVariable Long id, @RequestBody @Valid UserProfileDTO profile) {
        return new ResponseEntity<>(userProfileService.createProfile(id, profile), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@PathVariable Long id, @RequestBody  UserProfileDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileDTO));
    }
}
