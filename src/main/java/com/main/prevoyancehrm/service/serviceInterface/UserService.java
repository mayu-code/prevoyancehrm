package com.main.prevoyancehrm.service.serviceInterface;

import com.main.prevoyancehrm.entities.User;

public interface UserService {
    User registerUser(User user);
    User getUserByEmail(String email);
    User getUserById(long id);
    User getUserByJwt(String jwt);
}
