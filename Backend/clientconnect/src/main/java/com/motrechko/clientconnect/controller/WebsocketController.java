package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.TemplateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@Slf4j
public class WebsocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebsocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @SendTo("/topic/{businessId}/template")
    public void sendTemplateInfoToBusiness(TemplateDTO templateDTO,@DestinationVariable Long businessId) {
        simpMessagingTemplate.convertAndSend("/topic/" + businessId + "/template", templateDTO);
        log.info("Send websocket: " + templateDTO);
    }

}