package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.dto.CurrencyFilter;
import com.petprojects.currencyexchange.entity.Currency;
import com.petprojects.currencyexchange.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyDaoImpSQLite implements CurrencyDao {
    private static final CurrencyDaoImpSQLite INSTANCE;


    static {
        INSTANCE = new CurrencyDaoImpSQLite();
    }

    public static CurrencyDaoImpSQLite getInstance() {
        return INSTANCE;
    }

    private static final String SELECT_ALL = """
                    SELECT id, code, full_name, sign 
                    FROM currency
            """;

    private static final String SELECT_BY_ID = """
            SELECT id, code, full_name, sign 
            FROM currency
            WHERE id = ?
            """;

    private static final String INSERT_DATA = """
                    INSERT INTO currency (full_name, code, sign)
                    VALUES (?,?,?)
            """;
//    private static final String UPDATE_CUSTOMER_RENT_CAR_ID = """
//            UPDATE currency
//            SET RENTED_CAR_ID = ?
//            WHERE ID = ?
//            """;

    private CurrencyDaoImpSQLite() {
    }
    @Override
    public Currency add(Currency currency) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DATA)) {
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                currency.setId(resultSet.getInt(1));
            }
                return currency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Currency> findAll(CurrencyFilter currencyFilter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(currencyFilter.code() != null) {
            parameters.add(currencyFilter.code());
            whereSql.add("code = ?");
        }
        if(currencyFilter.fullName() != null) {
            parameters.add(currencyFilter.fullName());
            whereSql.add("full_name = ?");
        }
        parameters.add(currencyFilter.limit());
        parameters.add(currencyFilter.offset());
        String where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : "",
                " LIMIT ? OFFSET ? "
        ));
        String sql = SELECT_ALL + where;
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Currency> currencies = new ArrayList<>();
            for(int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String fullName = resultSet.getString("full_name");
                String sign = resultSet.getString("sign");
                Currency currency = new Currency(id, fullName, code, sign);
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
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
    public Optional<Currency> getCurrencyByCode(String code) {
        List<Currency> currencies = findAll(new CurrencyFilter(code, null, 1, 0));
        if(currencies.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(currencies.get(0));
    }

    @Override
    public Optional<Currency> getCurrencyById(int id, Connection connection) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Optional<Currency> currency = Optional.of(new Currency(resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("full_name"),
                        resultSet.getString("sign")));
                return currency;
            } else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
