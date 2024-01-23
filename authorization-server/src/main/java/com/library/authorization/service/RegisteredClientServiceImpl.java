package com.library.authorization.service;

import static java.util.Optional.ofNullable;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

@Service
public class RegisteredClientServiceImpl implements RegisteredClientService {

    public record ClientToRegister(String clientId, String redirectUri) {
    }

    public record ClientRegistered(String clientId, String secret) {
    }

    private RegisteredClientRepository registeredClientRepository;
    private PasswordEncoder passwordEncoder;

    public RegisteredClientServiceImpl(
            RegisteredClientRepository registeredClientRepository,
            PasswordEncoder passwordEncoder) {

        this.registeredClientRepository = registeredClientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Optional<RegisteredClient> loadRegisteredClientById(String clientId) {
        return ofNullable(registeredClientRepository.findByClientId(clientId));
    }

    // TODO: Make this method support the creation of the client only
    private ClientRegistered register(ClientToRegister clientToRegister) {

        final String secret = UUID.randomUUID().toString();

        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(clientToRegister.clientId())
                // TODO: extract a function to create/update the secret for a client
                .clientSecret(this.passwordEncoder.encode(secret))
                // TODO: extract a function to set authentication method for a client
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // TODO: extract a function to grant authorization type
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // TODO: extract a function to set redirect Uri for a client
                .redirectUri(clientToRegister.redirectUri())
                // TODO: extract a function to add scope for a client
                .scopes(c -> c.addAll(Collections.emptySet()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .clientIdIssuedAt(Instant.now())
                .build();

        registeredClientRepository.save(registeredClient);

        return new ClientRegistered(clientToRegister.clientId(), secret);
    }

    @Override
    public ClientRegistered registerClient(ClientToRegister clientToRegister) {

        ClientRegistered clientRegistered;

        Optional<RegisteredClient> registeredClientById = loadRegisteredClientById(clientToRegister.clientId());

        if (registeredClientById.isPresent()) {
            // TODO: Need to find a way to decode the secret before return it
            RegisteredClient registeredClient = registeredClientById.get();
            clientRegistered = new ClientRegistered(registeredClient.getClientId(), registeredClient.getClientSecret());

        } else {
            clientRegistered = register(clientToRegister);
        }

        return clientRegistered;
    }
}
