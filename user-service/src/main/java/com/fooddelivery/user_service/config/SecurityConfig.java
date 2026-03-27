package com.fooddelivery.user_service.config;
import com.fooddelivery.user_service.util.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.fooddelivery.user_service.util.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS configuration with proper source
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Disable CSRF for stateless JWT
                .csrf(csrf -> csrf.disable())

                // Stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        // ✅ Specific USER routes BEFORE the broad ADMIN wildcard
                        .requestMatchers("/api/users/cart/**").hasRole("USER")
                        .requestMatchers("/api/users/orders/**").hasRole("USER")
                        .requestMatchers("/api/users/delivery/**").hasRole("USER")  // ← add this
                        .requestMatchers(HttpMethod.GET, "/api/users/restaurants/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/users/menu/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/users/ratings").hasRole("USER")

                        // ✅ ADMIN routes after
                        .requestMatchers(HttpMethod.GET, "/api/users/*/ratings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        .requestMatchers("/api/orders/**").authenticated()
                        .requestMatchers("/api/cart/**").authenticated()

                        .anyRequest().authenticated()
                )
//                .authorizeHttpRequests(auth -> auth
//                        // Allow all OPTIONS requests (CORS preflight)
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//                        // PUBLIC endpoints
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
//
//                        // ADMIN endpoints
//                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/users/*/ratings").hasRole("ADMIN")
//
//                        // USER endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/users/*/ratings").hasRole("USER")
//
//                        // Authenticated endpoints
//                        .requestMatchers("/api/orders/**").authenticated()
//                        .requestMatchers("/api/cart/**").authenticated()
//
//                        // Any other request requires authentication
//                        .anyRequest().authenticated()
//                )

                // Add JWT filter
                .addFilterBefore(
                        jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /**
     * CORS Configuration Source - Fixed wildcard + credentials conflict
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ FIXED: Use allowedOriginPatterns instead of allowedOrigins when allowing credentials
        // Option 1: Allow all origins with patterns (for development)
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Option 2: Specific origins (for production)
        // configuration.setAllowedOrigins(Arrays.asList(
        //     "http://localhost:3000",
        //     "http://localhost:8080",
        //     "http://localhost:8082"
        // ));

        // Allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Allowed headers
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Exposed headers (if needed)
        configuration.setExposedHeaders(List.of("Authorization"));

        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}