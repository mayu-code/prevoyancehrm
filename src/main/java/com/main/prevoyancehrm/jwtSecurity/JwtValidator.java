package com.main.prevoyancehrm.jwtSecurity;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            String jwt = request.getHeader(JwtConstant.JWT_HEADER);
            if(jwt!=null){
                try{
                    String email = JwtProvider.getEmailFromToken(jwt);
                    String role = JwtProvider.getRoleFromToken(jwt);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, List.of(new SimpleGrantedAuthority(role)));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch(Exception e){
                    throw new BadCredentialsException("bad Credentials");
                }
            }
            filterChain.doFilter(request, response);
    }
    
}
