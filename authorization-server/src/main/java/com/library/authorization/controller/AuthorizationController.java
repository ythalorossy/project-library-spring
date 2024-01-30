package com.library.authorization.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.authorization.config.Authorization;
import com.library.authorization.service.RegisteredClientService;
import com.library.authorization.service.RegisteredClientServiceImpl.ClientRegistered;
import com.library.authorization.service.RegisteredClientServiceImpl.ClientToRegister;

import jakarta.ws.rs.NotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(Uris.AUTH_API)
public class AuthorizationController {

    record RequestClientRegistration(String clientId, String redirectUri) { };
    record ResponseClientRegistration(String clientId, String secret, String notes) { };
    record RequestAddScopesToClient(String clientId, String ...scopes) { };
    record ResponseAddScopesToClient(String clientId, String ...scopes) { };

    private RegisteredClientService registeredClientService;

    public AuthorizationController(RegisteredClientService registeredClientService) {
        this.registeredClientService = registeredClientService;
    }

    @PostMapping(Uris.REGISTER)
    @PreAuthorize(Authorization.OAUTH2_HAS_SCOPE_CLIENT_MANAGE)
    public ResponseEntity<ResponseClientRegistration> registerClient(
            @RequestBody RequestClientRegistration requestClientRegistrationDto) {

        ClientRegistered registeredClient = this.registeredClientService
                .registerClient(new ClientToRegister(requestClientRegistrationDto.clientId(),
                        requestClientRegistrationDto.redirectUri()));

        return ResponseEntity
                .ok(new ResponseClientRegistration(registeredClient.clientId(), registeredClient.secret(),
                        "The secret won't be visible anymore."));
    }

    @PostMapping(Uris.SCOPES)
    @PreAuthorize(Authorization.OAUTH2_HAS_SCOPE_CLIENT_MANAGE)
    public ResponseEntity<ResponseAddScopesToClient> addScopesToClient(@RequestBody RequestAddScopesToClient requestAddScopesToClient) {
        
        ClientRegistered clientRegistered;

        try {
            clientRegistered = this.registeredClientService
                    .addScopesToClient(requestAddScopesToClient.clientId, Set.of(requestAddScopesToClient.scopes));
                    
                    return ResponseEntity.ok(new ResponseAddScopesToClient(clientRegistered.clientId(), clientRegistered.scopes().stream().collect(Collectors.joining(","))));
        
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
    
}
