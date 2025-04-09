package com.mytech.shopmgmt.wrapper;

import java.util.Locale;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class AppResponseWrapper extends HttpServletResponseWrapper {

    public AppResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public Locale getLocale() {
        System.out.println("Wrapper class called by Filter");

        Locale locale = super.getLocale();
        String country = locale.getCountry();
        String language = locale.getLanguage();

        if (!country.equals("US")) {
            locale = Locale.US;
        }

        System.out.println(country);
        System.out.println(language);

        return locale;
    }
}
