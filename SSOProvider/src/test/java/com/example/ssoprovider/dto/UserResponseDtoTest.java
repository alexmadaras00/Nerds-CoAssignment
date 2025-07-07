package com.example.ssoprovider.dto;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserResponseDtoTest {

    @Test
    void testSettersAndGetters() {
        UserResponseDto dto = new UserResponseDto();

        Long id = 123L;
        String email = "user@example.com";
        String password = "password";

        dto.setId(id);
        dto.setEmail(email);
        dto.setPassword(password);

        assertEquals(id, dto.getId());
        assertEquals(email, dto.getEmail());
        assertEquals(password, dto.getPassword());
    }
}
