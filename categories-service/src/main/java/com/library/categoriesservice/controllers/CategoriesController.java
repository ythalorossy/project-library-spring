package com.library.categoriesservice.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {

    @GetMapping("/is-running")
    public ResponseEntity<String> isRunning() {

        String isRunning = "Categories Service is running. Current Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return ResponseEntity.ok(isRunning);
    }
    
}