package com.petprojects.currencyexchange.dao;
import com.petprojects.currencyexchange.model.Currency;
import com.petprojects.currencyexchange.model.ExchangeRate;
import com.petprojects.currencyexchange.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpSQLite implements ExchangeRateDao{
    private static final ExchangeRateDaoImpSQLite INSTANCE;
    private static final CurrencyDaoImpSQLite currencyDao = CurrencyDaoImpSQLite.getInstance();

    static {
        INSTANCE = new ExchangeRateDaoImpSQLite();
    }

    public static ExchangeRateDaoImpSQLite getInstance() {
        return INSTANCE;
    }

//    private static final String SELECT_ALL = "SELECT exchange_rate.id as id, exchange_rate.rate as rate, bc.id as bc_id, bc.full_name as bc_name, bc.code as bc_code, bc.sign as bc_sign, tc.id as tc_id, tc.full_name as tc_name, tc.code as tc_code, tc.sign as tc_sign" +
//            " FROM exchange_rate" +
//            " JOIN currency bc ON exchange_rate.base_currency_id = bc.id" +
//            " JOIN currency tc ON exchange_rate.target_currency_id = tc.id";

    private static final String SELECT_ALL = """
            SELECT id, exchange_rate.base_currency_id, exchange_rate.base_currency_id, exchange_rate.target_currency_id, exchange_rate.rate
            FROM exchange_rate;
    """;

    private static final String SELECT_BY_CODE_PAIR = """
            SELECT exchange_rate.id as id, exchange_rate.rate as rate, bc.id as bc_id, bc.full_name as bc_name, bc.code as bc_code, bc.sign as bc_sign, tc.id as tc_id, tc.full_name as tc_name, tc.code as tc_code, tc.sign as tc_sign
            FROM exchange_rate
            JOIN currency bc ON exchange_rate.base_currency_id = bc.id
            JOIN currency tc ON exchange_rate.target_currency_id = tc.id
            WHERE bc.code = ? AND tc.code = ?
            """;

    private static final String INSERT_EXCHANGE_RATE = """
        insert into exchange_rate (base_currency_id, target_currency_id, rate) values
        ((select id from currency where code = ?),(select id from currency where code = ?) , ?)
""";

    private static final String UPDATE_EXCHANGE_RATE_BY_CODE = """
            UPDATE exchange_rate
            SET rate = ?
            FROM (select id from currency where code = ?) as bc, (select id from currency as tc where code = ?) as tc
            WHERE exchange_rate.base_currency_id = bc.id AND exchange_rate.target_currency_id = tc.id
            """;

    private ExchangeRateDaoImpSQLite() {}

    @Override
    public void add(String baseCurrencyCode, String targetCurrencyCode, Double rate) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EXCHANGE_RATE);) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            preparedStatement.setDouble(3, rate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Currency baseCurrency = currencyDao.getCurrencyById(
                        resultSet.getInt("base_currency_id"), preparedStatement.getConnection()).get();
                Currency targetCurrency = currencyDao.getCurrencyById(resultSet.getInt("target_currency_id"), preparedStatement.getConnection()).get();
                double rate = resultSet.getDouble("rate");
                exchangeRates.add(new ExchangeRate(id, baseCurrency, targetCurrency, rate));
            }
            return exchangeRates;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Optional<ExchangeRate> getExchangeRateByCodePair(String baseCode, String targetCode) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CODE_PAIR);) {
            preparedStatement.setString(1, baseCode);
            preparedStatement.setString(2, targetCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Currency baseCurrency = new Currency(resultSet.getInt("bc_id"),
                        resultSet.getString("bc_code"),
                        resultSet.getString("bc_name"),
                        resultSet.getString("bc_sign"));
                Currency targetCurrency = new Currency(resultSet.getInt("tc_id"),
                        resultSet.getString("tc_code"),
                        resultSet.getString("tc_name"),
                        resultSet.getString("tc_sign"));
                double rate = resultSet.getDouble("rate");
                return Optional.of(new ExchangeRate(id, baseCurrency, targetCurrency, rate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Double rate, String baseCurrencyCode, String targetCurrencyCode) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXCHANGE_RATE_BY_CODE);) {
            preparedStatement.setDouble(1, rate);
            preparedStatement.setString(2, baseCurrencyCode);
            preparedStatement.setString(3, targetCurrencyCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
