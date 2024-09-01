package com.petprojects.currencyexchange.validator;

import com.petprojects.currencyexchange.dto.CreateUserDto;
import com.petprojects.currencyexchange.entity.Gender;
import com.petprojects.currencyexchange.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();
        if(Gender.getGender(userDto.getGender()).isEmpty()) {
            validationResult.addError(Error.of("invalid.gender", "Gender is invalid"));
        }
        if(Role.getRole(userDto.getRole()).isEmpty()) {
            validationResult.addError(Error.of("invalid.role", "Role is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
