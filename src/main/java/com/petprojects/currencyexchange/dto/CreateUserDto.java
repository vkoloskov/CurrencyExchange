package com.petprojects.currencyexchange.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String email;
    String password;
    String role;
    String gender;
}
