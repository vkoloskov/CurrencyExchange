package com.petprojects.currencyexchange.entity;

public class ExchangeDataResponse {

    Currency baseCurrency;
    Currency targetCurrency;
    Double rate;
    Double amount;
    Double convertedAmount;

    public ExchangeDataResponse(Currency baseCurrency, Currency targetCurrency, Double rate, Double amount, Double convertedAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }
}
