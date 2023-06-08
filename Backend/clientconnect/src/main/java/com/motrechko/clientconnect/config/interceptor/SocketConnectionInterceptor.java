package com.motrechko.clientconnect.config.interceptor;

import com.motrechko.clientconnect.security.JwtWebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;


/**
 * Interceptor validates headers in connect messages
 */
@Slf4j
public class SocketConnectionInterceptor extends SocketInterceptor {

    public SocketConnectionInterceptor(JwtWebSocketManager jwtWebSocketManager) {
        super(jwtWebSocketManager);
    }

    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            authenticateUser(accessor);
            return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
        }
        return message;
    }
}
