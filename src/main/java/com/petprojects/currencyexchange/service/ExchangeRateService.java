package com.petprojects.currencyexchange.service;

import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import com.petprojects.currencyexchange.dto.CurrencyDto;
import com.petprojects.currencyexchange.dto.ExchangeRateDto;
import com.petprojects.currencyexchange.entity.Currency;
import com.petprojects.currencyexchange.entity.ExchangeRate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDaoImpSQLite.getInstance();

    private ExchangeRateService() {}

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }

    public Optional<ExchangeRateDto> getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.getExchangeRateByCodePair(fromCurrencyCode, toCurrencyCode);
        if (optionalExchangeRate.isPresent()) {
            ExchangeRate exchangeRate = optionalExchangeRate.get();
            return Optional.of(ExchangeRateDto.builder().
                    baseCurrency(CurrencyDto.mapFrom(exchangeRate.getBaseCurrency())).
                    targetCurrency(CurrencyDto.mapFrom(exchangeRate.getTargetCurrency())).
                    rate(exchangeRate.getRate()).build());
        } else
            return Optional.empty();
    }
    public List<ExchangeRateDto> getAllExchangeRates() {
        return exchangeRateDao.getExchangeRates().stream().map(exchangeRate -> ExchangeRateDto.builder().
                baseCurrency(CurrencyDto.mapFrom(exchangeRate.getBaseCurrency())).
                targetCurrency(CurrencyDto.mapFrom(exchangeRate.getTargetCurrency())).
                rate(exchangeRate.getRate()).build()).collect(Collectors.toList());
    }
    public Optional<ExchangeRateDto> updateExchangeRate(Double rate, String baseCurrencyCode, String targetCurrencyCode) {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateDao.update(rate, baseCurrencyCode, targetCurrencyCode);
        return exchangeRateOptional.map(ExchangeRateDto::mapFrom);
    }
    public Optional<ExchangeRateDto> addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, Double rate) {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateDao.add(baseCurrencyCode,targetCurrencyCode, rate);
        return exchangeRateOptional.map(ExchangeRateDto::mapFrom);
    }
}
