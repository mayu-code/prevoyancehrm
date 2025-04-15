package com.main.prevoyancehrm.jwtSecurity;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.main.prevoyancehrm.entities.Sessions;
import com.main.prevoyancehrm.service.serviceImpl.SessionServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter{

    private final SessionServiceImpl sessionServiceImpl;

    public JwtValidator(SessionServiceImpl sessionServiceImpl){
        this.sessionServiceImpl=sessionServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            String jwt = request.getHeader(JwtConstant.JWT_HEADER);
            if(jwt!=null){
                Sessions session = this.sessionServiceImpl.getSessionByToken(jwt.substring(7));
                if(session==null || !session.isActive() || session.isDelete()){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token has expired, please login again\"}");
                    return;
                }
                try{
                    String email = JwtProvider.getEmailFromToken(jwt);
                    String role = JwtProvider.getRoleFromToken(jwt);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, List.of(new SimpleGrantedAuthority(role)));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch (ExpiredJwtException e) { 
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token has expired, please login again\"}");
                    return;
                } catch (Exception e) { 
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Invalid token, authentication failed\"}");
                    return;
                }
            }
            filterChain.doFilter(request, response);
    }
    
}
