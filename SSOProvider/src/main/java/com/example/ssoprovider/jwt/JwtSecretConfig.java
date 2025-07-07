package com.example.ssoprovider.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Configuration
public class JwtSecretConfig {

    @Bean
    public SecretKey generateSecretKey() {
        byte[] keyBytes = new byte[32]; // 256-bit for HS256
        new SecureRandom().nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}

