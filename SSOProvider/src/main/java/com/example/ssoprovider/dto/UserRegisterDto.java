package com.example.ssoprovider.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserRegisterDto {
    private String fullName;
    private String email;
    private String password;
    private Date dateOfBirth;
}
