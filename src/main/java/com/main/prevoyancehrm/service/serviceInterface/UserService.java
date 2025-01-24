package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.entities.User;

public interface UserService {
    User registerUser(User user);
    User getUserByEmail(String email);
    User getUserById(long id);
    User getUserByJwt(String jwt);
    List<Candidates> getAllCandidates(String query,String department);
    List<User> getAllEmployees(String query,String department);
}
