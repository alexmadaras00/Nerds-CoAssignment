package com.example.ssoprovider.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserResponseDtoTest {

    @Test
    void testSettersAndGetters() {
        UserResponseDto dto = new UserResponseDto();

        String id = "user9991234";
        String email = "user@example.com";

        dto.setId(id);
        dto.setEmail(email);


        Assertions.assertEquals(id, dto.getId());
        Assertions.assertEquals(email, dto.getEmail());
    }
}
