package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import com.petprojects.currencyexchange.dto.ExchangeRateDto;
import com.petprojects.currencyexchange.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/exchangeRates/*")
public class ExchangeRatesServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        List<ExchangeRateDto> exchangeRates = exchangeRateService.getAllExchangeRates();
        PrintWriter pw = response.getWriter();
        if (exchangeRates. isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            pw.println(gson.toJson(exchangeRates));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCode = request.getParameter("baseCurrencyCode");
        String targetCode = request.getParameter("targetCurrencyCode");
        Double rate = Double.valueOf(request.getParameter("rate"));

        PrintWriter pw = response.getWriter();
        Optional<ExchangeRateDto> exchangeRateDto = exchangeRateService.addExchangeRate(baseCode, targetCode, rate);
        if (exchangeRateDto.isPresent()) {
            pw.println(gson.toJson(exchangeRateDto.get()));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }




}
