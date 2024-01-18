package com.library.authorization.service;

import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisteredClientServiceImpl implements RegisteredClientService {

    private RegisteredClientRepository registeredClientRepository;

    public RegisteredClientServiceImpl(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    public void registerClient(RegisteredClient registeredClient) {
        if (!loadRegisteredClientById(registeredClient.getClientId()).isPresent()) {
            registeredClientRepository.save(registeredClient);
        }
    }

    public Optional<RegisteredClient> loadRegisteredClientById(String clientId) {
        return ofNullable(registeredClientRepository.findByClientId(clientId));
    }

}
