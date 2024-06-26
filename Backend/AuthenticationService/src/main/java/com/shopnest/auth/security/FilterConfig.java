package com.shopnest.auth.security;

import com.shopnest.auth.filter.JwtAdminFilter;
import com.shopnest.auth.filter.JwtUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Indicates this as a configuration class
 */
@Configuration
public class FilterConfig {
    /*
     *  Create a bean for FilterRegistrationBean.
     *  1. Register the JwtFilter For Both User And Admin Role
     *  2. add URL pattern for all requests so that any request for
     *     that URL pattern will be intercepted by the filter
     */


    @Bean
    public FilterRegistrationBean jwtAdminFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtAdminFilter());
        filter.addUrlPatterns("/api/v2/Admin");

        return filter;

    }

    @Bean
    public FilterRegistrationBean jwtUserFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtUserFilter());

        filter.addUrlPatterns("/api/v2/User");

        return filter;

    }
}
