package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dto.ExchangeRateDto;
import com.petprojects.currencyexchange.service.ExchangeRateService;
import com.petprojects.currencyexchange.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String currencyPair = ServletUtil.getPathParam(request);

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        if(currencyPair.length() != 6) {
            pw.println(gson.toJson(exchangeRateService.getAllExchangeRates()));
            response.setStatus(400);
        } else {
            String baseCode = currencyPair.substring(0,3);
            String targetCode = currencyPair.substring(3,6);
            Optional<ExchangeRateDto> exchangeRateDto = exchangeRateService.getExchangeRate(baseCode, targetCode);
            if(exchangeRateDto.isPresent()) {
                pw.println(gson.toJson((exchangeRateDto.get())));
            } else {
                response.setStatus(404);
            }
        }
    }
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var paramMap = ServletUtil.getParameterMap(request);
        String currencyPair = ServletUtil.getPathParam(request);
        if(currencyPair.length() != 6) {
            response.setStatus(404);
        }
        String baseCurrencyCode = currencyPair.substring(0,3);
        String targetCurrencyCode = currencyPair.substring(3,6);
        Double rate = Double.valueOf(paramMap.get("rate"));
        Optional<ExchangeRateDto> exchangeRateDto = exchangeRateService.updateExchangeRate(rate, baseCurrencyCode, targetCurrencyCode);
        PrintWriter pw = response.getWriter();
        if(exchangeRateDto.isPresent()) {
            pw.println(gson.toJson(exchangeRateDto.get()));
        } else {
            response.setStatus(404);
        }
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
