package com.library.accountservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


record AccountDto(String username, String password) { }

@RestController
public class AccountController {
    
    @PostMapping("/new")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        
        return ResponseEntity.ok(accountDto);
    }
    

}
