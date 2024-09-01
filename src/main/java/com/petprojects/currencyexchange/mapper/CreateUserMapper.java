package com.petprojects.currencyexchange.mapper;

import com.petprojects.currencyexchange.dto.CreateUserDto;
import com.petprojects.currencyexchange.entity.Gender;
import com.petprojects.currencyexchange.entity.Role;
import com.petprojects.currencyexchange.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Singular;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .gender(Gender.valueOf(createUserDto.getGender()))
                .build();
    }
}
