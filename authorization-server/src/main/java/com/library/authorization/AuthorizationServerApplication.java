package com.library.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(UserDetailsManager userDetailsManager) {

		return vars -> {

			UserDetails user = User.builder()
					.username("user")
					.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
					.roles("USER")
					.build();
			UserDetails admin = User.builder()
					.username("admin")
					.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
					.roles("USER", "ADMIN")
					.build();

			userDetailsManager.createUser(user);
			userDetailsManager.createUser(admin);
		};

	}

}
