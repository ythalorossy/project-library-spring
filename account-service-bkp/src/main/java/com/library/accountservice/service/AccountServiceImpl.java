package com.library.accountservice.service;

import com.library.accountservice.client.AuthServiceFeignClient;
import com.library.accountservice.dto.UserDto;
import com.library.accountservice.dto.UserRegistrationDto;

import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AuthServiceFeignClient authServiceFeignClient;

    public AccountServiceImpl(AuthServiceFeignClient authServiceFeignClient) {
        this.authServiceFeignClient = authServiceFeignClient;
    }

    @Override
    public UserDto create(UserRegistrationDto user) {
        return authServiceFeignClient.createUser(user);
    }
}