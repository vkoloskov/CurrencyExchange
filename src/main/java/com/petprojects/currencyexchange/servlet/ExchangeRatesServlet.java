package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exchangeRates/*")
public class ExchangeRatesServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDaoImpSQLite.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        pw.println(gson.toJson(exchangeRateDao.getExchangeRates()));

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCode = request.getParameter("baseCurrencyCode");
        String targetCode = request.getParameter("targetCurrencyCode");
        Double rate = Double.valueOf(request.getParameter("rate"));

        PrintWriter pw = response.getWriter();
        exchangeRateDao.add(baseCode, targetCode, rate);
        pw.println(gson.toJson(exchangeRateDao.getExchangeRateByCodePair(baseCode, targetCode)));
    }




}
