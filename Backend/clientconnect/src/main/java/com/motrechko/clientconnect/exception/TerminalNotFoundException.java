package com.motrechko.clientconnect.exception;

public class TerminalNotFoundException extends RuntimeException{
    public TerminalNotFoundException(String terminalUUID) {
        super("Terminal with UUID: " + terminalUUID + "not found");
    }
}
