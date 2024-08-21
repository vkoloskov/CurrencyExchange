package com.petprojects.currencyexchange.servlet;

import com.google.gson.Gson;
import com.petprojects.currencyexchange.dao.CurrencyDao;
import com.petprojects.currencyexchange.dao.CurrencyDaoImpSQLite;
import com.petprojects.currencyexchange.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();
    private final Gson gson = new Gson();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String code = ServletUtil.getPathParam(request);
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        if(code.isEmpty()) {
            pw.println("Code is empty");
            response.setStatus(400);
        } else {
            currencyService.getCurrency(code).ifPresentOrElse(currencyDto -> {
                        pw.println(gson.toJson(currencyDto));
                    },
                    () -> {
                        pw.println("Code is not found");
                        response.setStatus(404);
                    });
        }


    }

}
