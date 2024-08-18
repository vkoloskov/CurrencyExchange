package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDaoImpSQLite.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String currencyPair = ServletUtil.getPathParam(request);

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        if(currencyPair.isEmpty()) {
            pw.println(gson.toJson(exchangeRateDao.getExchangeRates()));
            response.setStatus(400);
        } else {
            String baseCode = currencyPair.substring(0,3);
            String targetCode = currencyPair.substring(3,6);
            pw.println(gson.toJson((exchangeRateDao.getExchangeRateByCodePair(baseCode, targetCode))));
        }

    }


    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var paramMap = ServletUtil.getParameterMap(request);
        String currencyPair = ServletUtil.getPathParam(request);
        String baseCurrencyCode = currencyPair.substring(0,3);
        String targetCurrencyCode = currencyPair.substring(3,6);
        Double rate = Double.valueOf(paramMap.get("rate"));
        exchangeRateDao.update(rate, baseCurrencyCode, targetCurrencyCode);
        PrintWriter pw = response.getWriter();

        pw.println(gson.toJson(exchangeRateDao.getExchangeRateByCodePair(baseCurrencyCode, targetCurrencyCode)));

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
        }

        this.doPatch(req, resp);
    }



}
