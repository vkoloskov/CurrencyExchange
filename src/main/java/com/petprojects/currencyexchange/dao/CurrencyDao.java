package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyDao {
    void add(Currency currency);
    List<Currency> findAll();
    public Optional<Currency> getCurrencyByCode(String code);
}
