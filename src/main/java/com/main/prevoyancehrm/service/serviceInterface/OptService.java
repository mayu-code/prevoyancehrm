package com.main.prevoyancehrm.service.serviceInterface;

public interface OptService {
    String generateOtp();
    boolean varifyOtp(String email,String otp);
    boolean varifySession(String email,String otp);
    String sendOtp(String email);
    boolean resetPassword(String email,String otp,String newPassword)throws Exception;
}
