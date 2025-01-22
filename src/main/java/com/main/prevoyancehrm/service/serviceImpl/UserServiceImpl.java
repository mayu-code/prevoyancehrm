package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.jwtSecurity.JwtProvider;
import com.main.prevoyancehrm.repository.UserRepo;
import com.main.prevoyancehrm.service.serviceInterface.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public User registerUser(User user) {
        return this.userRepo.save(user);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return this.userRepo.findByEmail(email);
    }

    @Override
    public User getUserById(long id) {
        return this.userRepo.findById(id).get();
    }

    @Override
    public User getUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return this.userRepo.findByEmail(email);
    }
    
}
