package com.petprojects.currencyexchange.servlet;

import com.google.common.base.Splitter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServletUtil {
    protected static String getPathParam(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        return pathInfo != null ? pathInfo.replaceFirst("/","") : "";
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {

        BufferedReader br = null;
        Map<String, String> dataMap = null;

        try {

            InputStreamReader reader = new InputStreamReader(
                    request.getInputStream());
            br = new BufferedReader(reader);

            String data = br.readLine();

            dataMap = Splitter.on('&')
                    .trimResults()
                    .withKeyValueSeparator(
                            Splitter.on('=')
                                    .limit(2)
                                    .trimResults())
                    .split(data);

            return dataMap;
        } catch (IOException ex) {
            Logger.getLogger(ServletUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServletUtil.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }

        return dataMap;
    }
}
