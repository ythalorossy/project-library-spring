package com.library.accountservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.accountservice.service.AuthorizationService;
import com.library.accountservice.service.AuthorizationService.RequestClientRegistration;
import com.library.accountservice.service.AuthorizationService.ResponseClientRegistration;
import com.library.accountservice.service.AuthorizationService.UnprotectedResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AccountController {

    private AuthorizationService authorizationService;

    public AccountController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/new/app")
    public ResponseEntity<ResponseClientRegistration> createAccount(
            @RequestBody RequestClientRegistration requestClientRegistration) {

        ResponseClientRegistration responseClientRegistration = this.authorizationService
                .registerClient(requestClientRegistration);

        return ResponseEntity.ok(responseClientRegistration);
    }

    @GetMapping("/unprotected")
    public ResponseEntity<UnprotectedResponse> unprotected(@RequestParam String param) {
        // This is just to validate the openfeign.
        UnprotectedResponse unprotectedResponse = this.authorizationService.unprotected();

        return ResponseEntity.ok(unprotectedResponse);
    }
}
