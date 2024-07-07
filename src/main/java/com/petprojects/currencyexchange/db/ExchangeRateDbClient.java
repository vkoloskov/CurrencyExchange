package com.petprojects.currencyexchange.db;

import com.petprojects.currencyexchange.model.Currency;
import com.petprojects.currencyexchange.model.ExchangeRate;

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

    public List<ExchangeRate> selectList(String query) {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                Double rate = resultSet.getDouble("rate");
                int bcId = resultSet.getInt("bc_id");
                String bcName = resultSet.getString("bc_name");
                String bcCode = resultSet.getString("bc_code");
                String bcSign = resultSet.getString("bc_sign");
                Currency baseCurrency = new Currency(bcId, bcName, bcCode, bcSign);
                int tcId = resultSet.getInt("tc_id");
                String tcName = resultSet.getString("tc_name");
                String tcCode = resultSet.getString("tc_code");
                String tcSign = resultSet.getString("tc_sign");
                Currency targetCurrency = new Currency(tcId, tcName, tcCode, tcSign);
                ExchangeRate exchangeRate = new ExchangeRate(id, baseCurrency, targetCurrency, rate);
                exchangeRateList.add(exchangeRate);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return exchangeRateList;
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
