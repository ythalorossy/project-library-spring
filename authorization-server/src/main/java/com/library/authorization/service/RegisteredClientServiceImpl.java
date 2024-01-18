package com.library.authorization.service;

import static java.util.Optional.ofNullable;

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
        if (!ofNullable(registeredClientRepository.findByClientId(registeredClient.getClientId())).isPresent()) {
            registeredClientRepository.save(registeredClient);
        }
    }

}
