package com.motrechko.clientconnect.service;


import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.motrechko.clientconnect.controller.WebsocketController;
import com.motrechko.clientconnect.payload.NfcScanMessageRequest;
import com.motrechko.clientconnect.dto.TemplateDTO;
import com.motrechko.clientconnect.exception.NfcScanMessageException;
import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.Template;
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
    private final TemplateService templateService;
    private final UserTemplateHistoryService userTemplateHistoryService;
    private final WebsocketController websocketController;

    @PostConstruct
    public void initialize(){
        subscribeToNfcScanTopic();
    }

    private void  subscribeToNfcScanTopic(){
        mqttClient.toAsync().subscribeWith()
                .topicFilter(NFC_SCAN_TOPIC)
                .callback(this::subscribeToNfcScanTopic)
                .send()
                .thenAccept(mqtt5SubAck -> log.info("Subscribed to topic {}", NFC_SCAN_TOPIC))
                .exceptionally(throwable -> {
                    log.error("Failed to subscribe to topic {}", NFC_SCAN_TOPIC, throwable);
                    return null;
                });
    }

    private void subscribeToNfcScanTopic(Mqtt5Publish mqtt5Publish){
        String messagePayload = new String(mqtt5Publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
        NfcScanMessageRequest nfcScanMessage = parseNfcScanMessage(messagePayload);
        log.info("Received NFC scan message. Terminal UUID: {}, card ID: {}", nfcScanMessage.getTerminalUUID(), nfcScanMessage.getCardId());

        processUserRecognition(nfcScanMessage);
    }

    private NfcScanMessageRequest parseNfcScanMessage(String messagePayload){
        String[] messageParts = messagePayload.split(":");

        if(messageParts.length < 2) {
            log.error("Failed to parse NFC scan message: {}", messagePayload);
            throw new NfcScanMessageException(messagePayload);
        }

        return NfcScanMessageRequest.builder()
                .terminalUUID(messageParts[0])
                .cardId(messageParts[1])
                .build();
    }

    private void processUserRecognition(NfcScanMessageRequest nfcScanMessage) {
        try {
            User recognizedUser = identifyUser(nfcScanMessage);
            Business recognizedBusiness = identifyBusiness(nfcScanMessage);
            TemplateDTO recognizedTemplate = findUserTemplate(recognizedUser, recognizedBusiness);
            recordUserActivity(recognizedUser, recognizedBusiness, recognizedTemplate);

        } catch (Exception e){
            log.error("Error during user recognition: {}", e.getMessage());
        }
    }

    private User identifyUser(NfcScanMessageRequest nfcScanMessageRequest){
        User user = userService.getUserByCardDetails(nfcScanMessageRequest);
        log.info("Found user {} by cardId {}", user.getEmail(), nfcScanMessageRequest.getCardId());
        return user;
    }

    private Business identifyBusiness(NfcScanMessageRequest nfcScanMessageRequest){
        Business business = businessService.getBusinessByTerminalId(nfcScanMessageRequest);
        log.info("Found Business {} by terminalUUID {}", business.getBusinessName(), nfcScanMessageRequest.getTerminalUUID());
        return business;
    }

    private TemplateDTO findUserTemplate(User user, Business business){
        TemplateDTO templateDTO = templateService.getUsersActiveTemplateByCategory(user, business.getCategory());
        log.info("Found users template {}", templateDTO);
        websocketController.sendTemplateInfoToBusiness(templateDTO, business.getId());
        return templateDTO;
    }

    private void recordUserActivity(User user, Business business, TemplateDTO templateDTO){
        Template template = new Template();
        template.setId(templateDTO.getId());
        userTemplateHistoryService.create(user, business, template);
    }
}
