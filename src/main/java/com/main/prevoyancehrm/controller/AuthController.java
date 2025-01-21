package com.main.prevoyancehrm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.RequestDto.RegisterRequest;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.jwtSecurity.CustomUserDetail;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class AuthController {
    
   @Autowired
   private UserServiceImpl userServiceImpl;

   @Autowired
   private CustomUserDetail customUserDetail;

   @SuppressWarnings("null")
    @PostMapping("/register")
   public ResponseEntity<SuccessResponse> registerUser(@RequestBody RegisterRequest request){
    SuccessResponse response = new SuccessResponse();
    User user = this.userServiceImpl.getUserByEmail(request.getEmail());
    if(user==null){
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage("Email Already present !");
        response.setHttpStatusCode(HttpStatusCode.valueOf(409));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    user.setEmail(request.getEmail());
    user.setContact(request.getContact());
    user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
    user.setName(request.getName());
    user.setRole(Role.EMPLOYEE);
    System.out.println(user.toString());
    try{
        this.userServiceImpl.registerUser(user);
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("you are Register Successfully !");
        response.setHttpStatusCode(HttpStatusCode.valueOf(200));
        return ResponseEntity.of(Optional.of(response));

    }catch(Exception e){
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(e.getMessage());
        response.setHttpStatusCode(HttpStatusCode.valueOf(500));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
   }
}
