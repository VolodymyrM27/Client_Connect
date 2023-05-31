package com.motrechko.clientconnect.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String cardId) {
        super("Card with id not found: " + cardId);
    }
}
