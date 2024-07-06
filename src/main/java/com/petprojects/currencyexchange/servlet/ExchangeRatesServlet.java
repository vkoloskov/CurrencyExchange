package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import com.petprojects.currencyexchange.model.Currency;
import com.petprojects.currencyexchange.model.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/exchangeRates/*")
public class ExchangeRatesServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpSQLite();
    private final Map<String, Currency> currencyMap = new HashMap<String, Currency>();

    {
        currencyMap.put("USD", new Currency(0, "US dollar", "USD", "$"));
        currencyMap.put("EUR", new Currency(1, "Euro", "EUR", "€"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String currencyPair = ServletUtil.getPathParam(request);

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        if(currencyPair.isEmpty()) {
            pw.println();
        } else {
            pw.println("{\n" +
                    "    \"id\": 0,\n" +
                    "    \"baseCurrency\": {\n" +
                    "        \"id\": 0,\n" +
                    "        \"name\": \"United States dollar\",\n" +
                    "        \"code\": \"USD\",\n" +
                    "        \"sign\": \"$\"\n" +
                    "    },\n" +
                    "    \"targetCurrency\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"name\": \"Euro\",\n" +
                    "        \"code\": \"EUR\",\n" +
                    "        \"sign\": \"€\"\n" +
                    "    },\n" +
                    "    \"rate\": 0.99\n" +
                    "}");
        }




    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String baseCode = request.getParameter("baseCurrencyCode");
        String targetCode = request.getParameter("targetCurrencyCode");
        Double rate = Double.valueOf(request.getParameter("rate"));
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(currencyMap.get(baseCode));
        exchangeRate.setTargetCurrency(currencyMap.get(targetCode));
        exchangeRate.setRate(rate);
        PrintWriter pw = response.getWriter();
        pw.println(gson.toJson(exchangeRate));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
        }

        this.doPatch(req, resp);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var paramMap = ServletUtil.getParameterMap(request);
        String currencyPair = ServletUtil.getPathParam(request);
        String baseCode = currencyPair.substring(0,2);
        String targetCurrencyCode = currencyPair.substring(3,5);
        //Double rate = Double.valueOf(request.getParameter("rate"));
        PrintWriter pw = response.getWriter();

    }



}
