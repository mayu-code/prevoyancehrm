package com.main.prevoyancehrm.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.RequestDto.CreatePasswordRequest;
import com.main.prevoyancehrm.dto.RequestDto.LoginRequest;
import com.main.prevoyancehrm.dto.RequestDto.RegisterRequest;
import com.main.prevoyancehrm.dto.RequestDto.ResetPassword;
import com.main.prevoyancehrm.dto.RequestDto.VarifyOpt;
import com.main.prevoyancehrm.dto.ResponseDto.OtpResponse;
import com.main.prevoyancehrm.dto.responseObjects.LoginResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.Sessions;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.exceptions.UnauthorizeException;
import com.main.prevoyancehrm.jwtSecurity.JwtProvider;
import com.main.prevoyancehrm.jwtSecurity.CustomUserDetail;
import com.main.prevoyancehrm.service.serviceImpl.OtpServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.SessionServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
   @Autowired
   private UserServiceImpl userServiceImpl;

   @Autowired
   private CustomUserDetail customUserDetail;

   @Autowired
   private SessionServiceImpl sessionServiceImpl;

   @Autowired
   private OtpServiceImpl otpServiceImpl;

   @PostMapping("/register")
   public ResponseEntity<SuccessResponse> registerUser(@RequestBody RegisterRequest request)throws Exception{
    SuccessResponse response = new SuccessResponse();
    User user1 = this.userServiceImpl.getUserByEmail(request.getEmail());
    if(user1!=null){
        if(user1.getRole()==Role.EMPLOYEE){
            response.setHttpStatus(HttpStatus.CREATED);
            response.setMessage("You are presnet as an Employee , you can contact with Admin!");
            response.setHttpStatusCode(409);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.setHttpStatus(HttpStatus.CREATED);
        response.setMessage("Email Already present !");
        response.setHttpStatusCode(409);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }  
    User user = new User();
    user.setEmail(request.getEmail());
    user.setMobileNo(request.getContact());
    user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
    user.setFirstName(request.getName());
    user.setRole(Role.HREXECUTIVE);
    try{
        this.userServiceImpl.registerUser(user);
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("you are Register Successfully !");
        response.setHttpStatusCode(200);
        System.out.println("ok");
        return ResponseEntity.of(Optional.of(response));
    }catch(Exception e){
        throw new Exception(e.getMessage());
    }
   }

   @PostMapping("/createPassword")
   public ResponseEntity<SuccessResponse> createPassword(@RequestBody CreatePasswordRequest request) throws Exception{
    SuccessResponse response = new SuccessResponse();
    User user = this.userServiceImpl.getUserByEmail(request.getEmail());
    if(user==null){
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setHttpStatusCode(500);;
        response.setMessage("User Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    if(user.getRole().equals(Role.CANDIDATE)){
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setHttpStatusCode(500);;
        response.setMessage("you are not onboarded yet !");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    if(user.getPassword()!=null){
        response.setHttpStatus(HttpStatus.ALREADY_REPORTED);
        response.setHttpStatusCode(208);
        response.setMessage("password already created !");
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
    }
    if(!request.getPassword().equals(request.getConfirmPassword())){
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setHttpStatusCode(500);;
        response.setMessage("Password and conform password not match !");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
    user.setModifyAt(LocalDateTime.now());
    this.userServiceImpl.updateUser(user);
    try{     
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpStatusCode(200);
        response.setMessage("password created successfully ! ");
        return ResponseEntity.of(Optional.of(response));
    }catch(Exception e){
        throw new Exception(e.getMessage());
    }
   }

   @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request)throws Exception{
        LoginResponse response = new LoginResponse();
        User user = this.userServiceImpl.getUserByEmail(request.getEmail());
        if(user==null){
            throw new EntityNotFoundException("invalid username or password");
        }
        if(user.getRole().equals(Role.EMPLOYEE)){
            throw new EntityNotFoundException("Your Are Not Aproved Yet as an HR Please contact Super Admin");
        }
        UserDetails userDetails = customUserDetail.loadUserByUsername(request.getEmail());
        boolean isPasswordValid = new BCryptPasswordEncoder().matches(request.getPassword(),userDetails.getPassword() );

        if(!isPasswordValid){
            throw new UnauthorizeException("invalid username of password");
        }

        Authentication authentication = authenticate(user.getEmail(), request.getPassword());
        String role = user.getRole().toString();
        Sessions session = JwtProvider.generateToken(authentication);
        session.setUser(user);
        session.setIssueAt(LocalDateTime.now());
        this.sessionServiceImpl.addSession(session);
        try{
            this.userServiceImpl.updateUser(user);
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setToken(session.getToken());
            response.setRole(role);
            response.setMessage("login successful ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
           throw new Exception(e.getMessage());
        }
    }

    private Authentication authenticate(String email , String password){
        UserDetails details = customUserDetail.loadUserByUsername(email);
        if(details==null){
            throw new UsernameNotFoundException("Invalid credentials ");
        }
        return new UsernamePasswordAuthenticationToken(details,password,details.getAuthorities());
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logOut(@RequestHeader("Authorization") String jwt) throws Exception {
        try {
            Sessions session = this.sessionServiceImpl.getSessionByToken(jwt.substring(7));
            if (session == null) {
                throw new EntityNotFoundException("session not found !");
            }
            session.setActive(false);
            session.setLogoutAt(LocalDateTime.now());
            session.setDelete(true);
            session.setDeleteDate(LocalDateTime.now());
            this.sessionServiceImpl.addSession(session);

            SuccessResponse response = new SuccessResponse(HttpStatus.OK, 200, "logout Successful ");
            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/send-otp/{email}")
    public ResponseEntity<?> sendOpt(@PathVariable("email") String email) throws Exception {
        User user = this.userServiceImpl.getUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("user not found !");
        }
        try {
            OtpResponse response = OtpResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("opt sended successfully")
                    .opt(otpServiceImpl.sendOtp(email)).build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> varifyOtp(@RequestBody VarifyOpt request) throws Exception {
        User user = this.userServiceImpl.getUserByEmail(request.getEmail());
        if (user == null) {
            throw new EntityNotFoundException("user not found !");
        }
        try {
            if (!otpServiceImpl.varifyOtp(request.getEmail(), request.getOpt())) {
                throw new BadRequestException("Invalid otp !");
            }
            SuccessResponse response = new SuccessResponse(HttpStatus.OK, 200, "otp sended Successfully !");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword request) throws Exception {
        User user = this.userServiceImpl.getUserByEmail(request.getEmail());
        if (user == null) {
            throw new EntityNotFoundException("user not found !");
        }
        try {
            if (!otpServiceImpl.resetPassword(request.getEmail(), request.getOpt(), request.getPassword())) {
                throw new BadRequestException("Invalid otp !");
            }
            SuccessResponse response = new SuccessResponse(HttpStatus.OK, 200, "Password Reset Successfully !");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
