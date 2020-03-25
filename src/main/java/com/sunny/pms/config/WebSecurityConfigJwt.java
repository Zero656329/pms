package com.sunny.pms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class WebSecurityConfigJwt extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler jwtAuthenticationFailureHandler;
}
