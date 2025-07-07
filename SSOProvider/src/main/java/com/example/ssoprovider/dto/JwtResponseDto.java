package com.example.ssoprovider.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JwtResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}

