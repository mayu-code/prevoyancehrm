package com.main.prevoyancehrm.jwtSecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JwtConstant {
    public static String JWT_HEADER = "Authorization";
    public static String SECRETE_KEY;
    public static String allowedOrigin;

    @Value("${secret.key}")
    private String apiKey;

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @PostConstruct
    public void init() {
        SECRETE_KEY = apiKey;
        allowedOrigin = allowedOrigins[0];
    }


    public static String getSECRETE_KEY() {
        return SECRETE_KEY;
    }

    public static String getAllowedOrigin() {
        return allowedOrigin;
    }

}
