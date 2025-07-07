package com.example.ssoprovider.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserLoginDtoTest {

    @Test
    void testSettersAndGetters() {
        UserLoginDto dto = new UserLoginDto();

        dto.setEmail("user@example.com");
        dto.setPassword("securePassword123");

        assertEquals("user@example.com", dto.getEmail());
        assertEquals("securePassword123", dto.getPassword());
    }
}
