package com.example.ssoprovider.jwt;

import com.example.ssoprovider.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JWTConfigTest {

    private SecretKey secretKey;
    private JWTConfig jwtConfig;
    private User testUser;

    @BeforeEach
    void setUp() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jwtConfig = new JWTConfig(secretKey);

        testUser = new User();
        testUser.setUserId("user-123");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("securepass");
        testUser.setFullName("Test User");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));
        testUser.setIsActive(true);
    }

    @Test
    void generateTokenShouldReturnValidToken() {
        String token = jwtConfig.generateToken(testUser);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsernameShouldReturnCorrectEmail() {
        String token = jwtConfig.generateToken(testUser);
        String mail = jwtConfig.extractUsername(token, secretKey);
        assertEquals(testUser.getEmail(), mail);
    }

    @Test
    void isTokenValidShouldReturnTrueForValidToken() {
        String token = jwtConfig.generateToken(testUser);
        assertTrue(jwtConfig.isTokenValid(token, secretKey));
    }

    @Test
    void isTokenValidShouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtConfig.isTokenValid(invalidToken, secretKey));
    }

    @Test
    void isTokenValidShouldReturnFalseForExpiredToken() throws InterruptedException {
        // Create a config with token expiring in 1ms
        JWTConfig shortLivedConfig = new JWTConfig(secretKey) {
            @Override
            public String generateToken(User user) {
                return Jwts.builder()
                        .setSubject(user.getEmail())
                        .setIssuedAt(new java.util.Date())
                        .setExpiration(new java.util.Date(System.currentTimeMillis() + 1)) // 1ms expiry
                        .signWith(secretKey, SignatureAlgorithm.HS256)
                        .compact();
            }
        };

        String token = shortLivedConfig.generateToken(testUser);
        Thread.sleep(10); // Let token expire
        assertFalse(shortLivedConfig.isTokenValid(token, secretKey));
    }

    @Test
    void extractUsernameShouldThrowForAlteredToken() {
        String token = jwtConfig.generateToken(testUser);
        // Alter token by removing part
        String tamperedToken = token.substring(0, token.length() - 10);
        assertThrows(Exception.class, () -> jwtConfig.extractUsername(tamperedToken, secretKey));
    }
}
