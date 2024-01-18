package com.library.authorization.controller;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

record RequestClientRegistrationDto(String clientId, String redirectUri) {
};

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private RegisteredClientRepository registeredClientRepository;

    public AuthorizationController(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisteredClient> registerClient(
            @RequestBody RequestClientRegistrationDto requestClientRegistrationDto) {

        final RegisteredClient client = Optional
                .ofNullable(registeredClientRepository.findByClientId(requestClientRegistrationDto.clientId()))
                .orElseGet(() -> {
                    RegisteredClient registeredClient = getRegisteredClient(requestClientRegistrationDto);

                    registeredClientRepository.save(registeredClient);

                    return registeredClient;
                });

        return ResponseEntity.ok(client);
    }

    private RegisteredClient getRegisteredClient(RequestClientRegistrationDto requestClientRegistrationDto) {
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(requestClientRegistrationDto.clientId())
                .clientSecret(new BCryptPasswordEncoder().encode(requestClientRegistrationDto.clientId()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri(requestClientRegistrationDto.redirectUri())
                .scopes(c -> c.addAll(Collections.emptySet()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .clientIdIssuedAt(Instant.now())
                .build();
        return registeredClient;
    }

}
