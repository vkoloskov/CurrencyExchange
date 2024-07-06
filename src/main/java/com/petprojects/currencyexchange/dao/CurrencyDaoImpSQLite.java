package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.db.CurrencyDbClient;
import com.petprojects.currencyexchange.model.Currency;
import org.sqlite.SQLiteDataSource;

import java.util.List;

public class CurrencyDaoImpSQLite implements CurrencyDao {
    private final CurrencyDbClient dbClient;

    private static final String CONNECTION_URL ="jdbc:sqlite:" + CurrencyDbClient.class.getResource("/currency_exchange.db");

    private static final String SELECT_ALL = "SELECT * FROM currency";

    private static final String SELECT_BY_CODE = "SELECT * FROM currency WHERE code = '%s'";

    private static final String INSERT_DATA = "INSERT INTO currency (full_name, code, sign) VALUES ('%s','%s','%s')";
    private static final String UPDATE_CUSTOMER_RENT_CAR_ID = "UPDATE currency " +
            "SET RENTED_CAR_ID = '%d' " +
            "WHERE ID = '%d'";

    public CurrencyDaoImpSQLite() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(CONNECTION_URL);
        this.dbClient = new CurrencyDbClient(dataSource);
    }
    @Override
    public void add(Currency currency) {
        dbClient.run(String.format(INSERT_DATA, currency.getName(), currency.getCode(), currency.getSign()));
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return dbClient.selectList(SELECT_ALL);
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        return dbClient.selectList(String.format(SELECT_BY_CODE, code)).get(0);
    }
}
