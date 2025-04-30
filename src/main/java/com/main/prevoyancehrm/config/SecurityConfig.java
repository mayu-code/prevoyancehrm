package com.main.prevoyancehrm.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.main.prevoyancehrm.jwtSecurity.CustomUserDetail;
import com.main.prevoyancehrm.jwtSecurity.JwtValidator;
import com.main.prevoyancehrm.service.serviceImpl.SessionServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetail customUserDetail;

    @Autowired
    private SessionServiceImpl sessionServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/hrExecutive/**").hasAnyRole("SUPERADMIN", "ADMIN", "HRMANAGER", "HREXECUTIVE")
                .requestMatchers("/hrManager/**").hasAnyRole("SUPERADMIN", "ADMIN", "HRMANAGER")
                .requestMatchers("/admin/**").hasAnyRole("SUPERADMIN", "ADMIN")
                .requestMatchers("/superAdmin/**").hasRole("SUPERADMIN")
                .requestMatchers("/user/**").hasAnyRole("SUPERADMIN", "ADMIN", "HRMANAGER", "HREXECUTIVE", "EMPLOYEE")
                .requestMatchers("/employee/**").hasAnyRole("SUPERADMIN", "ADMIN", "HRMANAGER", "HREXECUTIVE", "EMPLOYEE")
                .requestMatchers("/auth/**","swagger-ui/**","v3/**").permitAll()
                .anyRequest().authenticated())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(new JwtValidator(sessionServiceImpl), UsernamePasswordAuthenticationFilter.class); 
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetail);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
            configuration.setAllowedMethods(Arrays.asList("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
            configuration.setAllowCredentials(true);
            configuration.setExposedHeaders(Arrays.asList("Authorization"));
            configuration.setMaxAge(3600L);
            return configuration;
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
