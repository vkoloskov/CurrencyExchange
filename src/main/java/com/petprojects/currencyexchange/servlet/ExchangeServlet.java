package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.ExchangeRateDao;
import com.petprojects.currencyexchange.dao.ExchangeRateDaoImpSQLite;
import com.petprojects.currencyexchange.dto.ExchangeDataResponse;
import com.petprojects.currencyexchange.dto.ExchangeRateDto;
import com.petprojects.currencyexchange.entity.ExchangeRate;
import com.petprojects.currencyexchange.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final Gson gson = new Gson();

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDaoImpSQLite.getInstance();
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Double amount = Double.valueOf(request.getParameter("amount"));

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        Optional<ExchangeRateDto> optionalExchangeRate = exchangeRateService.getExchangeRate(from,to);
        ExchangeDataResponse exchangeDataResponse;

        if(optionalExchangeRate.isPresent()) {
            ExchangeRateDto exchangeRate = optionalExchangeRate.get();
            Double convertedAmount = amount * exchangeRate.getRate();
            exchangeDataResponse = new ExchangeDataResponse(exchangeRate.getBaseCurrency(),exchangeRate.getTargetCurrency(), exchangeRate.getRate(), amount, convertedAmount);
            pw.println(gson.toJson(exchangeDataResponse));
            return;
        }
        //check reverse pair
        optionalExchangeRate = exchangeRateService.getExchangeRate(to, from);
        if(optionalExchangeRate.isPresent()) {
            ExchangeRateDto exchangeRate = optionalExchangeRate.get();
            Double rate = 1/exchangeRate.getRate();
            Double convertedAmount = amount/rate;
            exchangeDataResponse = new ExchangeDataResponse(exchangeRate.getBaseCurrency(),exchangeRate.getTargetCurrency(), rate, amount, convertedAmount);
            pw.println(gson.toJson(exchangeDataResponse));
            return;
        }
        Optional<ExchangeRateDto> optionalExchangeRateUsdFrom = exchangeRateService.getExchangeRate("USD", from);
        Optional<ExchangeRateDto> optionalExchangeRateUsdTo = exchangeRateService.getExchangeRate("USD", to);
        if(optionalExchangeRateUsdFrom.isPresent() && optionalExchangeRateUsdTo.isPresent()) {
            ExchangeRateDto exchangeRateUsdFrom = optionalExchangeRateUsdFrom.get();
            ExchangeRateDto exchangeRateUsdTo = optionalExchangeRateUsdTo.get();
            Double rate = exchangeRateUsdFrom.getRate()/exchangeRateUsdTo.getRate();
            Double convertedAmount = amount/rate;
            exchangeDataResponse = new ExchangeDataResponse(exchangeRateUsdFrom.getBaseCurrency(),exchangeRateUsdTo.getTargetCurrency(), rate, amount, convertedAmount);
            pw.println(gson.toJson(exchangeDataResponse));
        } else {
            pw.println(gson.toJson("Валюта не найдена"));
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
