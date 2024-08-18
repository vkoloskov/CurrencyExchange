package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.model.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateDao {
    void add(String baseCurrencyCode, String targetCurrencyCode, Double rate);
    List<ExchangeRate> getExchangeRates();
    Optional<ExchangeRate> getExchangeRateByCodePair(String baseCode, String targetCode);

    void update(Double rate, String baseCurrencyCode, String targetCurrencyCode);
}
