package com.library.authorization.services;

import java.util.Set;

import com.library.authorization.services.RegisteredClientServiceImpl.ClientRegistered;
import com.library.authorization.services.RegisteredClientServiceImpl.ClientToRegister;

import jakarta.ws.rs.NotFoundException;

// TODO: Change the name if this interface. Maybe ClientService
public interface RegisteredClientService {

    public ClientRegistered registerClient(ClientToRegister clientToRegister);

    public ClientRegistered addScopesToClient(String clientId, Set<String> scopes) throws NotFoundException;
}
