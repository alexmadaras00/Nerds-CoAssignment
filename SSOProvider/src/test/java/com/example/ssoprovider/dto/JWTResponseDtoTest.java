package com.example.ssoprovider.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JWTResponseDtoTest {

    @Test
    void testConstructorSetsAccessToken() {
        String token = "sample-token";
        JwtResponseDto dto = new JwtResponseDto(token);
        assertEquals(token, dto.getAccessToken());
        assertEquals("Bearer", dto.getTokenType());
    }

    @Test
    void testSettersAndGetters() {
        JwtResponseDto dto = new JwtResponseDto("initial-token");

        dto.setAccessToken("new-token");
        assertEquals("new-token", dto.getAccessToken());

        dto.setTokenType("CustomType");
        assertEquals("CustomType", dto.getTokenType());
    }
}
