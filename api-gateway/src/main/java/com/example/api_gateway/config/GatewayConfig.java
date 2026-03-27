package com.example.api_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {



    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()


                //
                .route("user-service-register",
                        r -> r.path("/api/users/register")
                                .and().method(HttpMethod.POST)
                                .uri("lb://user-service"))

                .route("user-service-login",
                        r -> r.path("/api/users/login")
                                .and().method(HttpMethod.POST)
                                .uri("lb://user-service"))

                .route("user-service-protected",
                        r -> r.path("/api/users/**")
                                .and().method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                                .uri("lb://user-service"))

                .route("user-service-profile",
                        r -> r.path("/api/users/profile/**")
                                .uri("lb://user-service"))

                //
                .route("restaurant-service-public",
                        r -> r.path("/api/restaurants/**", "/api/menu/**")
                                .and().method(HttpMethod.GET)
                                .uri("lb://restaurant-service"))

                .route("restaurant-service-protected",
                        r -> r.path("/api/restaurants/**")
                                .and().method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                .uri("lb://restaurant-service"))

                .route("restaurant-service-menu",
                        r -> r.path("/api/menu/**")
                                .and().method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                .uri("lb://restaurant-service"))

                //
                .route("order-service",
                        r -> r.path("/api/orders/**")
                                .uri("lb://order-service"))

                .route("order-service-history",
                        r -> r.path("/api/order-history/**")
                                .uri("lb://order-service"))
                //
                .route("delivery-service",
                        r -> r.path("/api/delivery/**", "/api/tracking/**")
                                .uri("lb://delivery-service"))
                .route("deliverer-service",
                        r -> r.path("/api/deliverer/**", "/api/tracking/**")
                                .uri("lb://delivery-service"))

                // Admin Service
                .route("admin-service-login",
                        r -> r.path("/api/admin/auth/login")
                                .and().method(HttpMethod.POST)
                                .filters(f -> f.removeResponseHeader("Server"))
                                .uri("lb://admin-service"))

                .route("admin-service-admin",
                        r -> r.path("/api/admin/**")
                                .uri("lb://admin-service"))

                .route("admin-service-delivery-personnels",
                        r -> r.path("/api/delivery-personnels/**")
                                .uri("lb://admin-service"))
                .route("admin-service-restaurants",
                        r -> r.path("/api/restaurants/**")
                                .uri("lb://admin-service"))
                .route("admin-service-users",
                        r -> r.path("/api/users/**")
                                .uri("lb://admin-service"))

                .build();
    }
}