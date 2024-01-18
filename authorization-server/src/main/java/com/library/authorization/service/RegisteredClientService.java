package com.library.authorization.service;

import java.util.Optional;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface RegisteredClientService {

    public void registerClient(RegisteredClient registeredClient);

    public Optional<RegisteredClient> loadRegisteredClientById(String clientId);
}
