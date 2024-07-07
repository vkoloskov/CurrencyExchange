package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpSQLite();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        pw.println(gson.toJson(exchangeRateDao.getExchangeRates()));


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String baseCode = request.getParameter("baseCurrencyCode");
        String targetCode = request.getParameter("targetCurrencyCode");
        Double rate = Double.valueOf(request.getParameter("rate"));
//        ExchangeRate exchangeRate = new ExchangeRate();
//        exchangeRate.setBaseCurrency(currencyMap.get(baseCode));
//        exchangeRate.setTargetCurrency(currencyMap.get(targetCode));
//        exchangeRate.setRate(rate);
//        PrintWriter pw = response.getWriter();
//        pw.println(gson.toJson(exchangeRate));
    }




}
