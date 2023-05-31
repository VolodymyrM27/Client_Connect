package com.motrechko.clientconnect.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NfcScanMessageDTO {
    private String terminalUUID;
    private String cardId;
}
