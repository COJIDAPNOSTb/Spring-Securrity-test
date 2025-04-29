package com.example.ss_ex1.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthenticationLoggingFilter implements Filter {
    private final Logger logger = Logger.getLogger(AuthenticationLoggingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    var httpRequest = (HttpServletRequest)servletRequest;
    var requsetId = httpRequest.getHeader("Request-Id");
    logger.info("Request Id: " + requsetId);
    filterChain.doFilter(servletRequest, servletResponse);
    }
}
