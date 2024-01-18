package com.library.authorization.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.authorization.service.RegisteredClientService;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

record RequestClientRegistration(String clientId, String password, String redirectUri) {
};

record ResponseClientRegistration(String clientId, String password) {
};

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private RegisteredClientService registeredClientService;
    private PasswordEncoder passwordEncoder;

    public AuthorizationController(RegisteredClientService registeredClientService, PasswordEncoder passwordEncoder ) {
        this.registeredClientService = registeredClientService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseClientRegistration> registerClient(
            @RequestBody RequestClientRegistration requestClientRegistrationDto) {

            RegisteredClient registeredClient = getRegisteredClient(requestClientRegistrationDto);
            
            this.registeredClientService.registerClient(registeredClient);

        return ResponseEntity.ok(new ResponseClientRegistration(registeredClient.getClientId(), registeredClient.getClientSecret()));
    }

    private RegisteredClient getRegisteredClient(RequestClientRegistration requestClientRegistrationDto) {
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(requestClientRegistrationDto.clientId())
                .clientSecret(this.passwordEncoder.encode(requestClientRegistrationDto.clientId()))
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
