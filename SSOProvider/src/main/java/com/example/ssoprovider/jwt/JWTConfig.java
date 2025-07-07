package com.example.ssoprovider.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTConfig {

    // Injection automatically done. Spring Boot looks for Secret Key type.
    private final SecretKey jwtSecretKey;

    @Autowired
    public JWTConfig(SecretKey jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(jwtSecretKey,SignatureAlgorithm.HS256)
                .compact();

    }
    public String extractUsername(String token, SecretKey secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token, SecretKey secretKey){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);  // parses and validates signature and expiration
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // JwtException covers signature, malformed, expired, unsupported token exceptions
            // IllegalArgumentException covers null/empty token
            return false;
        }
    }

}
