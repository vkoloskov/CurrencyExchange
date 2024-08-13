package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.model.Currency;
import com.petprojects.currencyexchange.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpSQLite implements CurrencyDao {
    private static final CurrencyDaoImpSQLite INSTANCE;


    static {
        INSTANCE = new CurrencyDaoImpSQLite();
    }

    public static CurrencyDaoImpSQLite getInstance() {
        return INSTANCE;
    }

    private static final String SELECT_FROM_CURRENCY = "SELECT id, code, full_name, sign FROM currency";

    private static final String SELECT_BY_CODE = """
            SELECT id, code, full_name, sign FROM currency WHERE code = ?
            """;

    private static final String INSERT_DATA = """
            INSERT INTO currency (full_name, code, sign) VALUES (?,?,?)
    """;
    private static final String UPDATE_CUSTOMER_RENT_CAR_ID = """
            UPDATE currency
            SET RENTED_CAR_ID = ?
            WHERE ID = ?
            """;

    private CurrencyDaoImpSQLite() {
    }
    @Override
    public void add(Currency currency) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(INSERT_DATA)) {
            statement.setString(1, currency.getName());
            statement.setString(2, currency.getCode());
            statement.setString(3, currency.getSign());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Currency> getCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SELECT_FROM_CURRENCY)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String fullName = resultSet.getString("full_name");
                String sign = resultSet.getString("sign");
                Currency currency = new Currency(id, fullName, code, sign);
                currencies.add(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        return new Currency(1,"tt","r","f");
    }
}
