package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.dto.ResponseDto.UserResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.exceptions.DuplicateEntityException;
import com.main.prevoyancehrm.jwtSecurity.JwtProvider;
import com.main.prevoyancehrm.repository.UserRepo;
import com.main.prevoyancehrm.service.serviceInterface.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public User registerUser(User user) throws Exception{
        try {
            User user1 = this.userRepo.findByEmail(user.getEmail());
            if(user1!=null){
                throw new DuplicateEntityException("User Is Already Present");
            }
            String id = UUID.randomUUID().toString();
            user.setId(id);
            return this.userRepo.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
    }

    @Override
    public User updateUser(User user)throws Exception{
        try {
            return this.userRepo.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @Override
    public User getUserByEmail(String email) {
        return this.userRepo.findByEmail(email);
    }

    @Override
    public User getUserById(String id) {
        return this.userRepo.findById(id).orElse(null);
    }

    @Override
    public User getUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return this.userRepo.findByEmail(email);
    }

    @Override
    public List<Candidates> getAllCandidates(String query, String department) {
        Role role = Role.CANDIDATE;
        return this.userRepo.findAllCandidates(query, department, role);
    }

    @Override
    public List<Candidates> getAllEmployees(String query, String department) {
        Role role = Role.CANDIDATE;
        return this.userRepo.findAllEmployees(query,department,role);
    }

    @Override
    public void deleteCandidate(String id)throws Exception {
        try {
            if(!this.userRepo.existsUser(id)){
                throw new EntityNotFoundException("Employee Not Present !");
            }
            this.userRepo.deleteUser(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UserResponse getEmployeeById(String id) {
        UserResponse userResponse =this.userRepo.findUserDetailsById(id).orElse(null);
        return userResponse;
    }

    @Override
    public List<User> exportEmployee(String position, String department) {
        Role role = Role.CANDIDATE;
        return this.userRepo.importUsers(position,department,role);
    }

    @Override
    public List<Candidates> employeesBirthday() {
        Role role = Role.CANDIDATE;
        return this.userRepo.findUsersWithBirthdayToday(role);
    }

    @Override
    public Candidates getUserByMobileNo(String mobileNo) {
        Role role = Role.CANDIDATE;
        return this.userRepo.findUserByMobileNo(mobileNo, role);
    }

    @Override
    public List<User> getAllEmployees() {
        return this.userRepo.findAllEmployees(Role.CANDIDATE);
    }

    @Override
    public UserResponse getUserProfile(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return this.userRepo.getUserByEmail(email);
    }
    
}
