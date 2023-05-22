package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser_Id(Long id);

}
