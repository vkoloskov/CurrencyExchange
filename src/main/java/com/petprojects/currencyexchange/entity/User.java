package com.petprojects.currencyexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String email;
    private String password;
    private Role role;
    private Gender gender;

}
