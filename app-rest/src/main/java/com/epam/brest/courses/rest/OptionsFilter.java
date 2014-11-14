package com.epam.brest.courses.rest;

import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denis on 11/14/14.
 */
public class OptionsFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (httpServletResponse.getHeader("Access-Control-Allow-Origin") == null)
            httpServletResponse.addHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));

        httpServletResponse.addHeader("Access-Control-Allow-Origin", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod()))
            httpServletResponse.addHeader("Allow", "GET, POST, PUT, DELETE, OPTIONS");
        else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
