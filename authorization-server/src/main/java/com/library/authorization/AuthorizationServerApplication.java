package com.library.authorization;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.UserDetailsManager;

import com.library.authorization.service.RegisteredClientService;
import com.library.authorization.service.UserService;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(UserDetailsManager userDetailsManager, UserService userService,
			RegisteredClientService registeredClientService,
			PasswordEncoder passwordEncoder) {
		return vars -> {
			userService.createUser(User.builder()
					.username("user").roles("USER").password("password").build());
			userService.createUser(User.builder()
					.username("admin").roles("USER", "ADMIN").password("password").build());

			String clientId = "product-client";
			String clientSecret = "secret";
			String redirectUri = "http://127.0.0.1:8080/authorized";
			Consumer<Set<String>> scopes = c -> c.addAll(Set.of(
					"product.read",
					"product.write",
					"account.write",
					OidcScopes.OPENID,
					OidcScopes.PROFILE));

			RegisteredClient productClient = RegisteredClient
					.withId(UUID.randomUUID().toString())
					.clientId(clientId)
					.clientSecret(passwordEncoder.encode(clientSecret))
					.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
					.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
					.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
					.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
					.redirectUri(redirectUri)
					.scopes(scopes)
					.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
					.clientIdIssuedAt(Instant.now())
					.build();

			registeredClientService.registerClient(productClient);
		};
	}

}
