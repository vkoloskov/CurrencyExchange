package com.petprojects.currencyexchange.mapper;

public interface Mapper<T, F> {
    T mapFrom(F f);
}
