package com.petprojects.currencyexchange.dto;

import com.petprojects.currencyexchange.entity.Gender;
import com.petprojects.currencyexchange.entity.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Integer id;
    String email;
    String password;
    Role role;
    Gender gender;
}
