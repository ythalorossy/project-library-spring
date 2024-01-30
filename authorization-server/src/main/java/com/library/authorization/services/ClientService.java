package com.library.authorization.services;

import java.util.Set;

import com.library.authorization.services.ClientServiceImpl.ClientRegistered;
import com.library.authorization.services.ClientServiceImpl.ClientToRegister;

import jakarta.ws.rs.NotFoundException;

public interface ClientService {

    public ClientRegistered registerClient(ClientToRegister clientToRegister);

    public ClientRegistered addScopesToClient(String clientId, Set<String> scopes) throws NotFoundException;
}
