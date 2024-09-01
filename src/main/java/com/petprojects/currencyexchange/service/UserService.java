package com.petprojects.currencyexchange.service;

import com.petprojects.currencyexchange.dao.UserDao;
import com.petprojects.currencyexchange.dao.UserDaoImpSQLite;
import com.petprojects.currencyexchange.dto.CreateUserDto;
import com.petprojects.currencyexchange.dto.UserDto;
import com.petprojects.currencyexchange.entity.User;
import com.petprojects.currencyexchange.exception.ValidationException;
import com.petprojects.currencyexchange.mapper.CreateUserMapper;
import com.petprojects.currencyexchange.mapper.UserMapper;
import com.petprojects.currencyexchange.validator.CreateUserValidator;
import com.petprojects.currencyexchange.validator.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDaoImpSQLite.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();


    public Integer create(CreateUserDto createUserDto) {

        ValidationResult validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user = createUserMapper.mapFrom(createUserDto);
        user = userDao.save(user);
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password).map(userMapper::mapFrom);
    }
}
