package com.main.prevoyancehrm.jwtSecurity;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
    public static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());

    public static String generateToken(Authentication auth){
        String jwt = Jwts.builder()
                    .setIssuer("ok").setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+86400000))
                    .claim("email",auth.getName())
                    .claim("role", auth.getAuthorities().toArray()[0].toString())
                    .signWith(key)
                    .compact();

        return jwt;
    }

    public static String getEmailFromToken(String jwt){

            jwt = jwt.substring(7);
        Claims claims = Jwts.parser()
                        .setSigningKey(key).build()
                        .parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }

    public static String getRoleFromToken(String jwt) {
        
            jwt = jwt.substring(7);
        
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get("role").toString();
    }

     public static boolean isTokenExpired(String jwt) {
        try {
            jwt = jwt.substring(7); // Removing "Bearer " prefix
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            Date expiration = claims.getExpiration(); 
            return expiration.before(new Date()); // Return true if the token is expired
        } catch (ExpiredJwtException e) {
            return true; // Token has already expired
        } catch (Exception e) {
            throw new RuntimeException("Error checking token expiration: " + e.getMessage());
        }
    }
}
