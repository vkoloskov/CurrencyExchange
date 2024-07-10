package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExchangeServlet extends HttpServlet {
    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpSQLite();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        String currencyPair = ServletUtil.getPathParam(request);
//
//        response.setContentType("application/json");
//        PrintWriter pw = response.getWriter();
//
//        if(currencyPair.isEmpty()) {
//            pw.println(gson.toJson(exchangeRateDao.getExchangeRates()));
//            response.setStatus(400);
//        } else {
//            String baseCode = currencyPair.substring(0,3);
//            String targetCode = currencyPair.substring(3,6);
//            pw.println(gson.toJson((exchangeRateDao.getExchangeRateByCodePair(baseCode, targetCode))));
//        }

    }
}
