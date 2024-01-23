package com.library.accountservice.config;

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
                .requestMatchers(HttpMethod.POST, "/new/app")
                    .hasAuthority("SCOPE_account.write")
                .requestMatchers(HttpMethod.GET, "/unprotected")
                    .permitAll())
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
            ;

        return httpSecurity.build();

    }
}
