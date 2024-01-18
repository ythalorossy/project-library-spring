package com.library.authorization.service;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface RegisteredClientService {

    public void registerClient(RegisteredClient registeredClient);

}
