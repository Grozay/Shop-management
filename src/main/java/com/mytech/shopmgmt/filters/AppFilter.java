package com.mytech.shopmgmt.filters;

import com.mytech.shopmgmt.wrapper.AppRequestWrapper;
import com.mytech.shopmgmt.wrapper.AppResponseWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AppFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        AppRequestWrapper appRequestWrapper = new AppRequestWrapper(request);
        AppResponseWrapper appResponseWrapper = new AppResponseWrapper(response);
        chain.doFilter(appRequestWrapper, appResponseWrapper);
    }

}
