package com.library.accountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/actuator/**", "/actuator/info/**").permitAll()
                .requestMatchers("/unprotected").permitAll()
                .anyRequest().permitAll());
                
        return http.build();
    }

}
