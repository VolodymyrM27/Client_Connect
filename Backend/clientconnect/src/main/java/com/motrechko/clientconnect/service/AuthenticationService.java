package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.exception.EmailExistException;
import com.motrechko.clientconnect.model.Language;
import com.motrechko.clientconnect.payload.AuthenticationRequest;
import com.motrechko.clientconnect.payload.AuthenticationResponse;
import com.motrechko.clientconnect.payload.RegisterRequest;
import com.motrechko.clientconnect.security.JwtService;
import com.motrechko.clientconnect.model.Role;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new EmailExistException(request.getEmail());

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .languageSettings(Language.EN)
                .registrationDate(Instant.now())
                .lastLoginDate(Instant.now())
                .isBusiness(request.isBusiness())
                .role(request.isBusiness() ? Role.BUSINESS_USER : Role.NORMAL_USER)
                .build();
        userRepository.save(user);


        log.info("User {} successfully registered.", request.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username with login: " + request.getEmail() + " not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        user.setLastLoginDate(Instant.now());
        userRepository.save(user);
        log.info("User {} logged in successfully.", request.getEmail());

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
