package com.calculatorrestapi.rest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestIdentifierFilter> loggingFilter() {
        FilterRegistrationBean<RequestIdentifierFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestIdentifierFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
