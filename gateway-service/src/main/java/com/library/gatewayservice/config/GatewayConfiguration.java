package com.library.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("oauth2", r -> r
                    .path("/oauth2/**","/oauth2/authorize/**", "/oauth2/token/**")
                    .uri("lb://authorization-server"))
                .route("books", r -> r
                    .path("/books/**")
                    .uri("lb://books-service"))
                .route("categories", r -> r
                    .path("/categories/**")
                    .uri("lb://categories-service"))
                .build();
    }
}
