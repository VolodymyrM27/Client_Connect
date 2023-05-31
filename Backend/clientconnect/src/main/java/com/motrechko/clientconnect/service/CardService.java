package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.exception.CardNotFoundException;
import com.motrechko.clientconnect.model.Card;
import com.motrechko.clientconnect.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card getCardDetail(String cardId){
       return cardRepository.findByCardId(cardId).orElseThrow(
               () -> new CardNotFoundException(cardId)
       );
    }
}
