package com.example.ssoprovider.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ChangePasswordDtoTest {

    @Test
    public void testGettersAndSetters() {
        ChangePasswordDto dto = new ChangePasswordDto();

        dto.setOldPassword("oldPass123");
        dto.setNewPassword("newPass456");

        assertEquals("oldPass123", dto.getOldPassword());
        assertEquals("newPass456", dto.getNewPassword());


    }
    @Test
    public void testChanged() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setOldPassword("oldPass123");
        dto.setNewPassword("newPass456");
        assertNotEquals(dto.getNewPassword(),dto.getOldPassword());
    }
}
