package com.motrechko.clientconnect.config.interceptor;

import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.security.JwtWebSocketManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
@RequiredArgsConstructor
public abstract class SocketInterceptor implements ChannelInterceptor {

    protected static final String AUTHORIZATION_HEADER = "Authorization";
    protected static final String BEARER_PREFIX = "Bearer ";
    protected static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    protected final JwtWebSocketManager jwtWebSocketManager;

    protected void authenticateUser(StompHeaderAccessor accessor) {
        String token = getToken(accessor);
        User userDetails = jwtWebSocketManager.performAuthenticationWithJwt(token);
        accessor.setUser(userDetails);
    }

    protected String getToken(StompHeaderAccessor accessor) {
        String headerValue = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
        if (headerValue == null || !headerValue.startsWith(BEARER_PREFIX)) {
            log.warn("AuthHeader is null or doesn't start with correct prefix: [{}]", headerValue);
            throw new IllegalArgumentException("Invalid authorization header");
        }
        return headerValue.substring(BEARER_PREFIX_LENGTH);
    }
}
