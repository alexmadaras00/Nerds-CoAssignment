package com.example.ssoprovider.dto;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterDtoTest {

    @Test
    void testSettersAndGetters() {
        UserRegisterDto dto = new UserRegisterDto();

        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        Date dateOfBirth = Date.valueOf("1990-01-01");

        dto.setFullName(fullName);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setDateOfBirth(dateOfBirth);

        assertEquals(fullName, dto.getFullName());
        assertEquals(email, dto.getEmail());
        assertEquals(password, dto.getPassword());
        assertEquals(dateOfBirth, dto.getDateOfBirth());
    }
}
