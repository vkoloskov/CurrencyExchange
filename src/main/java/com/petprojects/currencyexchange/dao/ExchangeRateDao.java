package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.entity.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateDao {
    Optional<ExchangeRate> add(String baseCurrencyCode, String targetCurrencyCode, Double rate);
    List<ExchangeRate> getExchangeRates();
    Optional<ExchangeRate> getExchangeRateByCodePair(String baseCode, String targetCode);

    Optional<ExchangeRate> update(Double rate, String baseCurrencyCode, String targetCurrencyCode);
}
