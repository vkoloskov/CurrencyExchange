package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.model.Currency;

import java.util.List;

public interface CurrencyDao {
    void add(Currency currency);
    List<Currency> getCurrencies();
    public Currency getCurrencyByCode(String code);
}
