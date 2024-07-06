package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.db.CurrencyDbClient;
import com.petprojects.currencyexchange.db.ExchangeRateDbClient;
import com.petprojects.currencyexchange.model.ExchangeRate;
import org.sqlite.SQLiteDataSource;

import java.util.List;

public class ExchangeRateDaoImpSQLite implements ExchangeRateDao{
    private final ExchangeRateDbClient dbClient;
    private static final String CONNECTION_URL ="jdbc:sqlite:" + CurrencyDbClient.class.getResource("/currency_exchange.db");

    public ExchangeRateDaoImpSQLite() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(CONNECTION_URL);
        this.dbClient = new ExchangeRateDbClient(dataSource);
    }

    @Override
    public void add(ExchangeRate currency) {

    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return null;
    }

    @Override
    public ExchangeRate getExchangeRateByCodePair(String codePair) {
        return null;
    }
}
