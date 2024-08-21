package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.entity.Currency;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CurrencyDao {
    Currency add(Currency currency);
    List<Currency> findAll();
    Optional<Currency> getCurrencyByCode(String code);

    Optional<Currency> getCurrencyById(int id, Connection connection);
}
