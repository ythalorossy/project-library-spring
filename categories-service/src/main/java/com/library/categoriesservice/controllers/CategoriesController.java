package com.library.categoriesservice.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {

    record IsRunningResponse(String message, String currentTime, String port) {
    }

    private Environment environment;

    public CategoriesController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/is-running")
    public ResponseEntity<IsRunningResponse> isRunning() {

        String localServerPort = environment.getProperty("local.server.port");

        IsRunningResponse response = new IsRunningResponse(
                "Books Service is running",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                localServerPort);

        return ResponseEntity.ok(response);
    }

}