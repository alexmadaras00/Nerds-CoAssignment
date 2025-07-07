package com.example.ssoprovider.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JwtSecretConfig.class)
class JWTSecretConfigTest {

    @Autowired
    private SecretKey secretKey;

    @Test
    void contextLoads_andSecretKeyIsCreated() {
        assertNotNull(secretKey, "SecretKey bean should be created by Spring context");
        assertEquals("HmacSHA256", secretKey.getAlgorithm(), "SecretKey algorithm should be HmacSHA256");
        assertEquals(32, secretKey.getEncoded().length, "SecretKey byte length should be 32 (256-bit)");
    }
}
