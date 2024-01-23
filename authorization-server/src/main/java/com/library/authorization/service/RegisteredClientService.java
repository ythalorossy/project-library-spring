package com.library.authorization.service;

import com.library.authorization.service.RegisteredClientServiceImpl.ClientRegistered;
import com.library.authorization.service.RegisteredClientServiceImpl.ClientToRegister;

public interface RegisteredClientService {

    public ClientRegistered registerClient(ClientToRegister clientToRegister);
}
