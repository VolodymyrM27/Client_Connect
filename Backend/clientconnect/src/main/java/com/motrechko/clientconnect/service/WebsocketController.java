package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.TemplateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebsocketController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendTemplateInfoToBusiness(TemplateDTO templateDTO, Long businessId){
        simpMessagingTemplate.convertAndSend( + businessId + "/template", templateDTO);
        log.info("Send websocket: "+ templateDTO);
    }
}