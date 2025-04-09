package com.mytech.shopmgmt.filters;

import com.mytech.shopmgmt.helper.ServletHelper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class LoggedUserFilter
 */
@WebFilter("/dashboard")
public class LoggedUserFilter extends HttpFilter implements Filter {
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoggedUserFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;


        HttpSession session = httpServletRequest.getSession();

        if (session.getAttribute("username") == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServletHelper.redirect(httpServletRequest, httpServletResponse, "login");
            return;
        }else {
            ServletHelper.redirect(httpServletRequest, httpServletResponse, "dashboard.jsp      ");
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
