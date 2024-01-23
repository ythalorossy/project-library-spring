package com.library.accountservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("authorization-server")
public interface AuthorizationService {

    record RequestClientRegistration(String clientId, String redirectUri) {
    };

    record ResponseClientRegistration(String clientId, String secret, String notes) {
    };

    record UnprotectedResponse(String response) {

        public String getResponse() {
            return response;
        }
    };

    @PostMapping("/api/register")
    public ResponseClientRegistration registerClient(
            @RequestBody RequestClientRegistration requestClientRegistrationDto);

    @GetMapping("/unprotected")
    public UnprotectedResponse unprotected();

}