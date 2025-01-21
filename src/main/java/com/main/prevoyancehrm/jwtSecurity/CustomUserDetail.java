package com.main.prevoyancehrm.jwtSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.User;


@Service
public class CustomUserDetail implements UserDetailsService {

    @Autowired
    private com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl UserServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.UserServiceImpl.getUserByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException("invalid credentials");
        }

        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword())
                                                                 .roles(user.getRole().toString()).authorities(user.getAuthorities()).build();
    }
    
}
