package com.library.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.library.authorization.services.RegisteredClientService;
import com.library.authorization.services.UserService;
import com.library.authorization.services.RegisteredClientServiceImpl.ClientRegistered;
import com.library.authorization.services.RegisteredClientServiceImpl.ClientToRegister;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(UserService userService,
			RegisteredClientService registeredClientService,
			PasswordEncoder passwordEncoder) {
		return vars -> {
			userService.createUser(User.builder()
					.username("user").roles("USER").password("password").build());
			userService.createUser(User.builder()
					.username("admin").roles("USER", "ADMIN").password("password").build());

			final var clientId = "product-client";
			final var redirectUri = "http://127.0.0.1:8080/authorized";

			ClientRegistered registerClient = registeredClientService
					.registerClient(new ClientToRegister(clientId, redirectUri));
			System.out.println(registerClient.clientId() + " " + registerClient.secret());
		};
	}

}
