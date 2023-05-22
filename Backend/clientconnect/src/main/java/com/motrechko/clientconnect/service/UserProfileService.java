package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.UserProfileDTO;
import com.motrechko.clientconnect.exception.UserProfileAlreadyExistsException;
import com.motrechko.clientconnect.exception.UserProfileNotFoundException;
import com.motrechko.clientconnect.mapper.UserProfileMapper;
import com.motrechko.clientconnect.model.UserProfile;
import com.motrechko.clientconnect.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserService userService;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Transactional
    public UserProfileDTO updateUserProfile(Long userId, UserProfileDTO userProfileDTO) {
        UserProfile userProfile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new UserProfileNotFoundException(userId));

        UserProfile updatedProfile = userProfileMapper.map(userProfileDTO);
        updatedProfile.setId(userProfile.getId()); // Keep the same userId
        updatedProfile.setUser(userService.getUser(userId));

        if (userProfileDTO.getFirstName() != null) {
            userProfile.setFirstName(updatedProfile.getFirstName());
        }
        if (userProfileDTO.getLastName() != null) {
            userProfile.setLastName(updatedProfile.getLastName());
        }
        if (userProfileDTO.getDateOfBirth() != null) {
            userProfile.setDateOfBirth(updatedProfile.getDateOfBirth());
        }
        if (userProfileDTO.getGender() != null) {
            userProfile.setGender(updatedProfile.getGender());
        }
        if (userProfileDTO.getContactNumber() != null) {
            userProfile.setContactNumber(updatedProfile.getContactNumber());
        }
        if (userProfileDTO.getCountry() != null) {
            userProfile.setCountry(updatedProfile.getCountry());
        }
        if (userProfileDTO.getState() != null) {
            userProfile.setState(updatedProfile.getState());
        }
        if (userProfileDTO.getCity() != null) {
            userProfile.setCity(updatedProfile.getCity());
        }

        return userProfileMapper.map(userProfileRepository.save(userProfile));
    }

    public UserProfileDTO getUserProfile(Long userId) {
        return userProfileMapper.map(userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new UserProfileNotFoundException(userId)));
    }

    @Transactional
    public UserProfileDTO createProfile(Long userId, UserProfileDTO userProfileDTO) {
        if (userProfileRepository.findById(userId).isPresent()) {
            throw new UserProfileAlreadyExistsException(userId);
        }

        UserProfile userProfile = userProfileMapper.map(userProfileDTO);
        userProfile.setUser(userService.getUser(userId));

        return userProfileMapper.map(userProfileRepository.save(userProfile));
    }

}

