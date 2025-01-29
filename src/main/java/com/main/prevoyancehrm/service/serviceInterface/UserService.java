package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.entities.User;

public interface UserService {
    User registerUser(User user);
    User getUserByEmail(String email);
    User getUserById(long id);
    User getEmployeeById(long id);
    User getUserByJwt(String jwt);
    void deleteCandidate(long id);
    List<User> exportEmployee(String position,String department);
    List<Candidates> getAllCandidates(String query,String department);
    List<Candidates> getAllEmployees(String query,String department);
    List<Candidates> employeesBirthday();
    
}
