package com.motrechko.clientconnect.security;

import com.motrechko.clientconnect.exception.InvalidJwtTokenException;
import com.motrechko.clientconnect.exception.JwtTokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.exceptionResolver = exceptionResolver;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (isHeaderInvalid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = extractJwtFromHeader(authHeader);
        try {
            performAuthenticationWithJwt(jwt, request);
        } catch (JwtTokenExpiredException | InvalidJwtTokenException e) {
            exceptionResolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isHeaderInvalid(String authHeader) {
        return authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ");
    }

    private String extractJwtFromHeader(String authHeader) {
        return authHeader.substring(7);
    }

    private void performAuthenticationWithJwt(String jwt, HttpServletRequest request) {
        if (jwtService.isTokenExpired(jwt)) {
            throw new JwtTokenExpiredException();
        }

        String userEmail = jwtService.extractUsername(jwt);

        if (isUserNotAuthenticated(userEmail)) {
            authenticateUser(jwt, userEmail, request);
        }
    }

    private boolean isUserNotAuthenticated(String userEmail) {
        return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void authenticateUser(String jwt, String userEmail, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        if (!jwtService.isTokenValid(jwt, userDetails)) {
            throw new InvalidJwtTokenException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

