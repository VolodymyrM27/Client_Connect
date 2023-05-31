package com.motrechko.clientconnect.exception;

public class NfcScanMessageException extends RuntimeException {
    public NfcScanMessageException(String messagePayload) {
        super("Failed to parse NFC scan message: " + messagePayload);
    }
}
