package com.library.accountservice.controller;

import com.library.accountservice.dto.UserDto;
import com.library.accountservice.dto.UserRegistrationDto;
import com.library.accountservice.service.AccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public UserDto createNewAccount(@RequestBody UserRegistrationDto user) {
		return accountService.create(user);
	}

}