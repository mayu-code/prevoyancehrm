package com.main.prevoyancehrm.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.RequestDto.LoginRequest;
import com.main.prevoyancehrm.dto.RequestDto.RegisterRequest;
import com.main.prevoyancehrm.dto.responseObjects.LoginResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.jwtSecurity.JwtProvider;
import com.main.prevoyancehrm.jwtSecurity.CustomUserDetail;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

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
    User user1 = this.userServiceImpl.getUserByEmail(request.getEmail());
    if(user1!=null){
        response.setHttpStatus(HttpStatus.CREATED);
        response.setMessage("Email Already present !");
        response.setHttpStatusCode(409);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    if(user1.getRole()==Role.EMPLOYEE){
        response.setHttpStatus(HttpStatus.CREATED);
        response.setMessage("You are presnet as a Employee , you can contact with Admin!");
        response.setHttpStatusCode(409);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    User user = new User();
    user.setEmail(request.getEmail());
    user.setContact(request.getContact());
    user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
    user.setName(request.getName());
    user.setRole(Role.HREXECUTIVE);
    System.out.println(user.toString());
    try{
        this.userServiceImpl.registerUser(user);
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("you are Register Successfully !");
        response.setHttpStatusCode(200);
        return ResponseEntity.of(Optional.of(response));

    }catch(Exception e){
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(e.getMessage());
        response.setHttpStatusCode(500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
   }

   @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request){
        LoginResponse response = new LoginResponse();
        User user = this.userServiceImpl.getUserByEmail(request.getEmail());
        if(user==null){
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setHttpStatusCode(500);;
            response.setMessage("Invalid email or password");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
        }
        if(user.getRole().equals(Role.EMPLOYEE)){
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setHttpStatusCode(401);;
            response.setMessage("Your Are Not Aproved Yet as a HR Please contact Super Admin");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
        }
        UserDetails userDetails = customUserDetail.loadUserByUsername(request.getEmail());
        boolean isPasswordValid = new BCryptPasswordEncoder().matches(request.getPassword(),userDetails.getPassword() );

        if(!isPasswordValid){
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setHttpStatusCode(500);;
            response.setMessage("Invalid email or password");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
        }

        Authentication authentication = authenticate(user.getEmail(), request.getPassword());
        String role = user.getRole().toString();
        String token = JwtProvider.generateToken(authentication);
        try{
           
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setToken(token);
            response.setRole(role);
            response.setMessage("login successful ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setHttpStatusCode(500);;
            response.setMessage("something went wrong !");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
        }
    }

    private Authentication authenticate(String email , String password){
        UserDetails details = customUserDetail.loadUserByUsername(email);
        if(details==null){
            throw new UsernameNotFoundException("Invalid credentials ");
        }

        return new UsernamePasswordAuthenticationToken(details,password,details.getAuthorities());

    }
}
