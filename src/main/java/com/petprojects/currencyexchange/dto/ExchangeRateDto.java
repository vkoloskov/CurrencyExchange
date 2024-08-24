package com.petprojects.currencyexchange.dto;

import com.petprojects.currencyexchange.entity.Currency;
import com.petprojects.currencyexchange.entity.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExchangeRateDto {
    private Integer id;
    private CurrencyDto baseCurrency;
    private CurrencyDto targetCurrency;
    private Double rate;

    public static ExchangeRateDto mapFrom(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(exchangeRate.getId(), CurrencyDto.mapFrom(exchangeRate.getBaseCurrency()), CurrencyDto.mapFrom(exchangeRate.getTargetCurrency()), exchangeRate.getRate());
    }

}
