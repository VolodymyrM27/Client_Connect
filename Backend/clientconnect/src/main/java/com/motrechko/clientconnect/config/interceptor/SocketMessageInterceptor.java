package com.motrechko.clientconnect.config.interceptor;

import com.motrechko.clientconnect.security.JwtWebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Slf4j
public class SocketMessageInterceptor extends SocketInterceptor {

    public SocketMessageInterceptor(JwtWebSocketManager jwtWebSocketManager) {
        super(jwtWebSocketManager);
    }

    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.SEND) {
            authenticateUser(accessor);
        }
        return message;
    }
}
