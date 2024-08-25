package com.petprojects.currencyexchange.dto;

import com.petprojects.currencyexchange.entity.Currency;

public class ExchangeDataResponse {

    CurrencyDto baseCurrency;
    CurrencyDto targetCurrency;
    Double rate;
    Double amount;
    Double convertedAmount;

    public ExchangeDataResponse(CurrencyDto baseCurrency, CurrencyDto targetCurrency, Double rate, Double amount, Double convertedAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }
}
