package com.petprojects.currencyexchange.db;

import com.petprojects.currencyexchange.model.Currency;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDbClient {
    private DataSource dataSource;

    public ExchangeRateDbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Currency> selectList(String query) {
        List<Currency> currencyList = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("base_currency_id");
                String fullName = resultSet.getString("target_currency_id");
                String sign = resultSet.getString("rate");
                Currency currency = new Currency(id, fullName, code, sign);
                currencyList.add(currency);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return currencyList;
    }

    public void run(String query) {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();) {

            connection.setAutoCommit(true);
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
