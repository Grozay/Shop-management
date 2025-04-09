package com.mytech.shopmgmt.filters;

import com.mytech.shopmgmt.helper.ServletHelper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet Filter implementation class UsernameFilter
 */
@WebFilter(urlPatterns = {
        "/users", "/login"
}, initParams = {
        @WebInitParam(name = "notAllowedNames", value = "facebook, google, zalo, microsoft")
})
public class UsernameFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;
    private String[] namesNotAllowed;

    /**
     * @see HttpFilter#HttpFilter()
     */
    public UsernameFilter() {
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
        String username = httpServletRequest.getParameter("username");

        if (checkUsernameNotAllowed(username)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletRequest.setAttribute("message", "Username is not allowed");
            ServletHelper.forward(httpServletRequest, httpServletResponse, "login");
            return;
        }
        chain.doFilter(request, response);
    }

    public boolean checkUsernameNotAllowed(String username) {
        for (String name : namesNotAllowed) {
            if (name.equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        String notAllowedNames = fConfig.getInitParameter("notAllowedNames");
        namesNotAllowed = notAllowedNames.split(",");
        System.out.println("Name are not allowed: " + namesNotAllowed);
    }

}
