package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.CurrencyDao;
import com.petprojects.currencyexchange.dao.CurrencyDaoImpSQLite;
import com.petprojects.currencyexchange.model.Currency;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currencies/*")
public class CurrenciesServlet extends HttpServlet {

    private CurrencyDao currencyDao = new CurrencyDaoImpSQLite();
    private Gson gson = new Gson();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();

        pw.println(gson.toJson(currencyDao.getAllCurrencies()));


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");
        var currency = new Currency(name, code, sign);
        currencyDao.add(currency);
        currency = currencyDao.getCurrencyByCode(code);
        PrintWriter pw = response.getWriter();
        pw.println(gson.toJson(currency));
    }

}
