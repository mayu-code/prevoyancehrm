package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;


import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.dto.ResponseDto.UserResponse;
import com.main.prevoyancehrm.entities.User;

public interface UserService {

    User registerUser(User user) throws Exception;
    User updateUser(User user)throws Exception;

    User getUserByEmail(String email)throws Exception;
    User getUserById(String id)throws Exception;
    UserResponse getEmployeeById(String id)throws Exception;
    UserResponse getUserProfile(String email)throws Exception;
    User getUserByJwt(String jwt)throws Exception;

    void deleteCandidate(String id)throws Exception;
    
    List<User> exportEmployee(String position,String department)throws Exception;
    List<User> getAllEmployees()throws Exception;
    List<Candidates> getAllCandidates(String query,String department)throws Exception;
    List<Candidates> getAllEmployees(String query,String department)throws Exception;
    List<Candidates> employeesBirthday()throws Exception;
    Candidates getUserByMobileNo(String mobileNo)throws Exception;
    
}
