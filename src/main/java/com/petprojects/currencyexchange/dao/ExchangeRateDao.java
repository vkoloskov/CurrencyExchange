package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.model.ExchangeRate;

import java.util.List;

public interface ExchangeRateDao {
    void add(String baseCurrencyCode, String targetCurrencyCode, Double rate);
    List<ExchangeRate> getExchangeRates();
    public ExchangeRate getExchangeRateByCodePair(String baseCode, String targetCode);

    void update(Double rate, String baseCurrencyCode, String targetCurrencyCode);
}
