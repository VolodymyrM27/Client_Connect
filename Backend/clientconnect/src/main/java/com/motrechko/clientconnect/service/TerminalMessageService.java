package com.motrechko.clientconnect.service;


import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.motrechko.clientconnect.dto.NfcScanMessageDTO;
import com.motrechko.clientconnect.exception.NfcScanMessageException;
import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class TerminalMessageService {
    private static final String NFC_SCAN_TOPIC = "nfc/scan";
    private final Mqtt5Client mqttClient;
    private final UserService userService;
    private final BusinessService businessService;

    @PostConstruct
    public void subscribeToTopics(){
        mqttClient.toAsync().subscribeWith()
                .topicFilter(NFC_SCAN_TOPIC)
                .callback(this::handleNfcScanMessage)
                .send()
                .thenAccept(mqtt5SubAck -> log.info("Subscribed to topic {}", NFC_SCAN_TOPIC))
                .exceptionally(throwable -> {
                    log.error("Failed to subscribe to topic {}", NFC_SCAN_TOPIC, throwable);
                    return null;
                });
    }

    private void handleNfcScanMessage(Mqtt5Publish mqtt5Publish) {
        String messagePayload = new String(mqtt5Publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
        String[] messageParts = messagePayload.split(":");

        if(messageParts.length < 2) {
            log.error("Failed to parse NFC scan message: {}", messagePayload);
            throw new NfcScanMessageException(messagePayload);
        }

        NfcScanMessageDTO nfcScanMessage = buildNfcScanMessageDTO(messageParts[0], messageParts[1]);

        log.info("Received NFC scan message. Terminal UUID: {}, card ID: {}", nfcScanMessage.getTerminalUUID(), nfcScanMessage.getCardId());
        try {
            recognizeUser(nfcScanMessage);
        } catch (Exception e){
            log.error("Error during recognized user: {}", e.getMessage() );
        }
    }

    private NfcScanMessageDTO buildNfcScanMessageDTO(String terminalUUID, String cardId) {
        return NfcScanMessageDTO.builder()
                .terminalUUID(terminalUUID)
                .cardId(cardId)
                .build();
    }

    private void recognizeUser(NfcScanMessageDTO nfcScanMessageDTO){
        User user = userService.getUserByCardDetails(nfcScanMessageDTO);
        log.info("Found user {} by cardId {}", user.getEmail(), nfcScanMessageDTO.getCardId());
        Business business = businessService.getBusinessByTerminalId(nfcScanMessageDTO);
        log.info("Found Business {} by terminalUUID {}", business.getBusinessName(), nfcScanMessageDTO.getTerminalUUID());
    }
}
