package com.uit.microservice_booking_service.common.config;

import com.uit.microservice_base_project.config.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
@Configuration
public class SecurtityConfig extends BaseSecurityConfig {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

//        http.antMatcher("/api/v1/host/**").authorizeRequests().anyRequest().authenticated();

    }

}
