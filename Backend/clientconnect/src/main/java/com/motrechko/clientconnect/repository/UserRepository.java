package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findAllByEmail(String email);
}
