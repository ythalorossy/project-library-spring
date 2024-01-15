package com.library.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JwtSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/product")
                    .hasAuthority("SCOPE_product.read")
                .requestMatchers(HttpMethod.POST, "/product")
                    .hasAuthority("SCOPE_product.write"))
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return httpSecurity.build();

    }
}
