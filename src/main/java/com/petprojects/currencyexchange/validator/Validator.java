package com.petprojects.currencyexchange.validator;

public interface Validator <T> {
    ValidationResult isValid(T t);
}
