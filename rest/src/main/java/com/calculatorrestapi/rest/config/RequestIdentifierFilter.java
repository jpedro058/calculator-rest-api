package com.calculatorrestapi.rest.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@WebFilter("/*")
public class RequestIdentifierFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Generate a unique requestId and set it in the MDC
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        // Add the requestId to the response header
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("X-Request-ID", requestId);

        // Proceed with the request
        chain.doFilter(request, response);

        // Clean up MDC after request processing
        MDC.remove("requestId");
    }

    @Override
    public void destroy() {
        
    }
}
