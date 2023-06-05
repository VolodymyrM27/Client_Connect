package com.motrechko.clientconnect.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NfcScanMessageRequest {
    private String terminalUUID;
    private String cardId;
}
