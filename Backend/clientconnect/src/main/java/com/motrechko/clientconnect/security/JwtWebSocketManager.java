package com.motrechko.clientconnect.security;

import com.motrechko.clientconnect.exception.InvalidJwtTokenException;
import com.motrechko.clientconnect.exception.JwtTokenExpiredException;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtWebSocketManager {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;


    // TODO:  Refactor the class, remove duplicate code as in JwtAuthenticationFilter
    public User performAuthenticationWithJwt(String jwt) {
        if (jwtService.isTokenExpired(jwt)) {
            throw new JwtTokenExpiredException();
        }
        String userEmail = jwtService.extractUsername(jwt);
        if (isUserNotAuthenticated(userEmail)) {
            return authenticateUser(jwt, userEmail);
        }
        return null;
    }

    private boolean isUserNotAuthenticated(String userEmail) {
        return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private User authenticateUser(String jwt, String userEmail) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        User user = userService.getUserByEmail(userEmail);

        if (!jwtService.isTokenValid(jwt, userDetails)) {
            throw new InvalidJwtTokenException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return user;
    }
}
