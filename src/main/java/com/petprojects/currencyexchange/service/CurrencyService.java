package com.petprojects.currencyexchange.service;

import com.petprojects.currencyexchange.dao.CurrencyDao;
import com.petprojects.currencyexchange.dao.CurrencyDaoImpSQLite;
import com.petprojects.currencyexchange.dto.CurrencyDto;
import com.petprojects.currencyexchange.entity.Currency;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyService {
    private final CurrencyDao currencyDao = CurrencyDaoImpSQLite.getInstance();

    private final static CurrencyService INSTANCE = new CurrencyService();

    private CurrencyService() {}

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    public Optional<CurrencyDto> getCurrency(String currencyCode) {
        Optional<Currency> currencyOptional = currencyDao.getCurrencyByCode(currencyCode);
        if (currencyOptional.isPresent()) {
            Currency currency = currencyOptional.get();
            return Optional.of(
                    new CurrencyDto(currency.getId(),
                            currency.getCode(),
                            currency.getName(),
                            currency.getSign()));
        } else
            return Optional.empty();
    }

    public List<CurrencyDto> getAllCurrencies() {
        return currencyDao.findAll().stream().map(currency -> new CurrencyDto(currency.getId(),
                currency.getCode(),
                currency.getName(),
                currency.getSign())).
                collect(Collectors.toList());
    }

    public CurrencyDto add(CurrencyDto currencyDto) {
        Currency currency = currencyDao.add(new Currency(currencyDto.getCode(), currencyDto.getName(), currencyDto.getSign()));
        return new CurrencyDto(currency.getId(), currency.getCode(), currency.getName(), currency.getSign());
    }
}
