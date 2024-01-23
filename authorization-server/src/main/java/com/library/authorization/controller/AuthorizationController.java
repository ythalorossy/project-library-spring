package com.library.authorization.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.authorization.service.RegisteredClientService;
import com.library.authorization.service.RegisteredClientServiceImpl.ClientRegistered;
import com.library.authorization.service.RegisteredClientServiceImpl.ClientToRegister;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

record RequestClientRegistration(String clientId, String redirectUri) {
};

record ResponseClientRegistration(String clientId, String secret, String notes) {
};

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private RegisteredClientService registeredClientService;

    public AuthorizationController(RegisteredClientService registeredClientService) {
        this.registeredClientService = registeredClientService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseClientRegistration> registerClient(
            @RequestBody RequestClientRegistration requestClientRegistrationDto) {

        ClientRegistered registeredClient = this.registeredClientService
                .registerClient(new ClientToRegister(requestClientRegistrationDto.clientId(),
                        requestClientRegistrationDto.redirectUri()));

        return ResponseEntity
                .ok(new ResponseClientRegistration(registeredClient.clientId(), registeredClient.secret(),
                        "The secret won't be visible anymore."));
    }
}
