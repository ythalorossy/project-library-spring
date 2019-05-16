package com.library.accountservice.client;

import com.library.accountservice.dto.UserDto;
import com.library.accountservice.dto.UserRegistrationDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;

@FeignClient(name = "auth-service", fallbackFactory = AuthServiceFeignClient.AuthServiceFeignClientFactory.class)
public interface AuthServiceFeignClient {

    @PostMapping(value = "/uaa/user")
    UserDto createUser(@RequestBody UserRegistrationDto user);


    @Component
    public static class AuthServiceFeignClientFactory implements FallbackFactory<AuthServiceFeignClient> {

        @Override
        public AuthServiceFeignClient create(Throwable cause) {

            return new AuthServiceFeignClient(){
                @Override
                public UserDto createUser(UserRegistrationDto user) {
                    UserDto userDto = new UserDto();
                    userDto.setUsername("not valid: " + cause.getMessage());
                    return userDto;
                }
            };
        }
    }

}
