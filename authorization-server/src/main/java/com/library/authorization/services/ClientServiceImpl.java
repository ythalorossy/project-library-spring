package com.library.authorization.services;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import com.library.authorization.exceptions.ClientNotFoundException;

@Service
public class ClientServiceImpl implements ClientService {

    public record ClientToRegister(String clientId, String redirectUri) {
    }

    public record ClientRegistered(String clientId, String secret, Set<String> scopes) {
    }

    private RegisteredClientRepository registeredClientRepository;
    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(
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
                .scopes(c -> c.addAll(emptySet()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .clientIdIssuedAt(Instant.now())
                .build();

        registeredClientRepository.save(registeredClient);

        return new ClientRegistered(clientToRegister.clientId(), secret, emptySet());
    }

    @Override
    public ClientRegistered registerClient(ClientToRegister clientToRegister) {

        ClientRegistered clientRegistered;

        Optional<RegisteredClient> registeredClientById = loadRegisteredClientById(clientToRegister.clientId());

        if (registeredClientById.isPresent()) {
            RegisteredClient client = registeredClientById.get();
            clientRegistered = new ClientRegistered(client.getClientId(), client.getClientSecret(), emptySet());

        } else {
            clientRegistered = register(clientToRegister);
        }

        return clientRegistered;
    }

    @Override
    public ClientRegistered addScopesToClient(String clientId, Set<String> scopes) throws ClientNotFoundException {

        RegisteredClient client = loadRegisteredClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException());

        final Builder fromClientBuilder = RegisteredClient.from(client);
        for (String scope : scopes) {
            fromClientBuilder.scope(scope);
        }

        RegisteredClient clientToBeUpdated = fromClientBuilder.build();

        this.registeredClientRepository.save(clientToBeUpdated);

        return new ClientRegistered(clientToBeUpdated.getClientId(), null, clientToBeUpdated.getScopes());
    }
}
