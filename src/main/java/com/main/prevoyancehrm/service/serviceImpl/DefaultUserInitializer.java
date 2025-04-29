package com.main.prevoyancehrm.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.repository.UserRepo;

@Component
public class DefaultUserInitializer implements CommandLineRunner{

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepo.existsByIsDeleteFalse()){
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setRole(Role.SUPERADMIN);
            user.setActive(true);
            user.setApproved(true);
            user.setEmail("durbulemayur9265@gmail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("Mayur@123"));
            user.setActive(true);
            this.userRepo.save(user);
        }
    }
    
}
