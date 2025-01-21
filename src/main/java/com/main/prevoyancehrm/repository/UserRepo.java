package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.main.prevoyancehrm.entities.User;


public interface UserRepo extends JpaRepository<User,Long>{
    User findByEmail(String email);
}
