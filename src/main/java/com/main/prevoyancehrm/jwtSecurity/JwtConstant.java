package com.main.prevoyancehrm.jwtSecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JwtConstant {
    public static String JWT_HEADER = "Authorization";
    public static String SECRETE_KEY;

    @Value("${secret.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        SECRETE_KEY = apiKey;
    }

    public static String getSECRETE_KEY() {
        return SECRETE_KEY;
    }

}
