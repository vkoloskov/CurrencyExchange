package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.model.ExchangeRate;

import java.util.List;

public interface ExchangeRateDao {
    void add(ExchangeRate currency);
    List<ExchangeRate> getExchangeRates();
    public ExchangeRate getExchangeRateByCodePair(String codePair);
}
