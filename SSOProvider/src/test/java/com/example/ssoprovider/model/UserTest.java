package com.example.ssoprovider.model;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testAllGettersAndSetters() {
        User user = new User();

        String userId = "user39532";
        String email = "user@example.com";
        String password = "12345677";
        String fullName = "Lexx";
        LocalDate birthDate = LocalDate.of(1999, 11, 30);
        Boolean isActive = true;

        // Default value for isActive should be false
        assertFalse(user.getIsActive());

        // Set all fields
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setBirthDate(birthDate);
        user.setIsActive(isActive);

        // Verify all getters return expected values
        assertEquals(userId, user.getUserId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(fullName, user.getFullName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(isActive, user.getIsActive());

        // Test setting isActive back to false
        user.setIsActive(false);
        assertFalse(user.getIsActive());
    }
}
