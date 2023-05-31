package com.motrechko.clientconnect.repository;

import com.motrechko.clientconnect.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardId(String cardId);
}