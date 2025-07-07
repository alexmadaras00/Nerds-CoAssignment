package com.example.ssoprovider.jwt;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTConfigTest {

    private SecretKey secretKey;
    private JWTConfig jwtConfig;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        secretKey = io.jsonwebtoken.security.Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        jwtConfig = new JWTConfig(secretKey);
        userDetails = mock(UserDetails.class);

    }

    @Test
    void generateTokenShouldReturnValidToken() {
        String token = jwtConfig.generateToken(userDetails);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsernameShouldReturnSubject() {
        String token = jwtConfig.generateToken(userDetails);
        String username = jwtConfig.extractUsername(token, secretKey);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void isTokenValidShouldReturnTrueForValidToken() {
        String token = jwtConfig.generateToken(userDetails);
        assertTrue(jwtConfig.isTokenValid(token, secretKey));
    }

    @Test
    void isTokenValidShouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtConfig.isTokenValid(invalidToken, secretKey));
    }

    @Test
    void isTokenValidShouldReturnFalseForExpiredToken() throws InterruptedException {
        // Create a token with very short expiration
        JWTConfig shortExpConfig = new JWTConfig(secretKey) {
            @Override
            public String generateToken(UserDetails userDetails) {
                return io.jsonwebtoken.Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new java.util.Date())
                        .setExpiration(new java.util.Date(System.currentTimeMillis() + 10)) // 10 ms expiration
                        .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS256)
                        .compact();
            }
        };

        String token = shortExpConfig.generateToken(userDetails);
        Thread.sleep(20); // Wait until token expires

        assertFalse(shortExpConfig.isTokenValid(token, secretKey));
    }
}
