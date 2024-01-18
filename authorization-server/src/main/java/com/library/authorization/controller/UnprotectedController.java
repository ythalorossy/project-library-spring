package com.library.authorization.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

record UnprotectedResponse(String response) {
};

@RestController
public class UnprotectedController {

    @GetMapping("/unprotected")
    public ResponseEntity<UnprotectedResponse> unprotected() {

        return ResponseEntity.ok(new UnprotectedResponse("Unprotected information"));
    }
}
