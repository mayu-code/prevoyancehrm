package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.ResponseDto.Candidates;
import com.main.prevoyancehrm.dto.ResponseDto.UserResponse;
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
        User user =this.userRepo.findById(id).get();
        user.setPassword(null);
        return user;
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
    public void deleteCandidate(long id) {
         this.userRepo.deleteById(id);
         return;
    }
    
}
