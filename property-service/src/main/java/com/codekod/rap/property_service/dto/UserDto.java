package com.codekod.rap.property_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private String emailId;
    private String password;
    private String fullName;
    private List<String> roles;
}
