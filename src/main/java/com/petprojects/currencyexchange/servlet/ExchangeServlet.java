package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import com.petprojects.currencyexchange.entity.ExchangeDataResponse;
import com.petprojects.currencyexchange.entity.ExchangeRate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDaoImpSQLite.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Double amount = Double.valueOf(request.getParameter("amount"));

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        ExchangeRate exchangeRate = exchangeRateDao.getExchangeRateByCodePair(from,to).get();
        ExchangeDataResponse exchangeDataResponse;

        if(exchangeRate != null) {
            Double convertedAmount = amount * exchangeRate.getRate();
            exchangeDataResponse = new ExchangeDataResponse(exchangeRate.getBaseCurrency(),exchangeRate.getTargetCurrency(), exchangeRate.getRate(), amount, convertedAmount);
            pw.println(gson.toJson(exchangeDataResponse));
            return;
        }
        //check reverse pair
        exchangeRate = exchangeRateDao.getExchangeRateByCodePair(to, from).get();
        if(exchangeRate != null) {
            Double rate = 1/exchangeRate.getRate();
            Double convertedAmount = amount/rate;
            exchangeDataResponse = new ExchangeDataResponse(exchangeRate.getBaseCurrency(),exchangeRate.getTargetCurrency(), rate, amount, convertedAmount);
            pw.println(gson.toJson(exchangeDataResponse));
            return;
        }
        ExchangeRate exchangeRateUsdFrom = exchangeRateDao.getExchangeRateByCodePair("USD", from).get();
        ExchangeRate exchangeRateUsdTo = exchangeRateDao.getExchangeRateByCodePair("USD", to).get();
        if(exchangeRateUsdFrom == null || exchangeRateUsdTo == null) {
            pw.println(gson.toJson(new HashMap<String, String>().put("message", "Валюта не найдена")));
        } else {
            Double rate = exchangeRateUsdFrom.getRate()/exchangeRateUsdTo.getRate();
            Double convertedAmount = amount/rate;
            exchangeDataResponse = new ExchangeDataResponse(exchangeRateUsdFrom.getBaseCurrency(),exchangeRateUsdTo.getTargetCurrency(), rate, amount, convertedAmount);
            pw.println(gson.toJson(exchangeDataResponse));
        }

    }
}
