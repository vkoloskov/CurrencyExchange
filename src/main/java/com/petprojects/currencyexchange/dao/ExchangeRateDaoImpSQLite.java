package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.db.CurrencyDbClient;
import com.petprojects.currencyexchange.db.ExchangeRateDbClient;
import com.petprojects.currencyexchange.model.ExchangeRate;
import org.sqlite.SQLiteDataSource;

import java.util.List;

public class ExchangeRateDaoImpSQLite implements ExchangeRateDao{
    private final ExchangeRateDbClient dbClient;

    private static final String CONNECTION_URL ="jdbc:sqlite:" + CurrencyDbClient.class.getResource("/currency_exchange.db");

    private static final String SELECT_ALL = "SELECT exchange_rate.id as id, exchange_rate.rate as rate, bc.id as bc_id, bc.full_name as bc_name, bc.code as bc_code, bc.sign as bc_sign, tc.id as tc_id, tc.full_name as tc_name, tc.code as tc_code, tc.sign as tc_sign" +
            " FROM exchange_rate" +
            " JOIN currency bc ON exchange_rate.base_currency_id = bc.id" +
            " JOIN currency tc ON exchange_rate.target_currency_id = tc.id";

    private static final String SELECT = """
        
    """;

    private static final String SELECT_BY_CODE_PAIR = "SELECT exchange_rate.id as id, exchange_rate.rate as rate, bc.id as bc_id, bc.full_name as bc_name, bc.code as bc_code, bc.sign as bc_sign, tc.id as tc_id, tc.full_name as tc_name, tc.code as tc_code, tc.sign as tc_sign" +
            " FROM exchange_rate" +
            " JOIN currency bc ON exchange_rate.base_currency_id = bc.id" +
            " JOIN currency tc ON exchange_rate.target_currency_id = tc.id" +
            " WHERE bc.code = '%s' AND tc.code = '%s'";

    private static final String INSERT_DATA = "insert into exchange_rate (base_currency_id, target_currency_id, rate) values" +
            " ((select id from currency where code = '%s'),(select id from currency where code = '%s') , '%.3f')";

    private static final String UPDATE_CUSTOMER_RENT_CAR_ID = "UPDATE exchange_rate" +
            " SET rate = '%.3f'" +
            " FROM (select id from currency where code = '%s') as bc, (select id from currency as tc where code = '%s') as tc" +
            " WHERE exchange_rate.base_currency_id = bc.id AND exchange_rate.target_currency_id = tc.id";

    public ExchangeRateDaoImpSQLite() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(CONNECTION_URL);
        this.dbClient = new ExchangeRateDbClient(dataSource);
    }

    @Override
    public void add(String baseCurrencyCode, String targetCurrencyCode, Double rate) {
        dbClient.run(String.format(INSERT_DATA, baseCurrencyCode, targetCurrencyCode, rate));
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return dbClient.selectList(SELECT_ALL);
    }

    @Override
    public ExchangeRate getExchangeRateByCodePair(String baseCode, String targetCode) {
        var exchangeRate = dbClient.selectList(String.format(SELECT_BY_CODE_PAIR, baseCode, targetCode));
        return exchangeRate.isEmpty() ? null : exchangeRate.get(0);
    }

    @Override
    public void update(Double rate, String baseCurrencyCode, String targetCurrencyCode) {
        dbClient.run(String.format(UPDATE_CUSTOMER_RENT_CAR_ID, rate, baseCurrencyCode, targetCurrencyCode));
    }
}
