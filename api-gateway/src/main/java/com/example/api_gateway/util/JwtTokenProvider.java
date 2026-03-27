package com.example.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:b8f1339f-bb76-4d62-abd9-4adde208ee3f}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    /**
     * Generates a JWT token for the given username without additional claims.
     * 
     * @param username the username to include in the token
     * @return the generated JWT token as a String
     */
    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    /**
     * Generates a JWT token for the given username with custom claims.
     * The token includes the username as the subject (sub claim), 
     * issued time, and expiration time based on configuration.
     * 
     * @param username the username to include in the token
     * @param claims a map of custom claims to include in the token
     * @return the generated JWT token as a String
     */
    public String generateToken(String username, Map<String, Object> claims) {
        claims.put("sub", username);
        
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     * The username is stored as the subject (sub) claim in the token.
     * 
     * @param token the JWT token to extract the username from
     * @return the username extracted from the token
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Validates the integrity and expiration of a JWT token.
     * Returns true if the token is valid and not expired, false otherwise.
     * 
     * @param token the JWT token to validate
     * @return true if the token is valid, false if it's invalid or expired
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parses and extracts all claims from a JWT token.
     * This is a private helper method used internally to get token claims.
     * 
     * @param token the JWT token to parse
     * @return the Claims object containing all claims from the token
     */
    private Claims getClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
