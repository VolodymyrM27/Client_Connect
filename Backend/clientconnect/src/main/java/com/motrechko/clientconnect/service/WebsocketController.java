package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.TemplateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebsocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebsocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    public void sendTemplateInfoToBusiness(TemplateDTO templateDTO, Long businessId) {
        simpMessagingTemplate.convertAndSend("/topic/" + businessId + "/template", templateDTO);
        log.info("Send websocket: " + templateDTO);
    }

}