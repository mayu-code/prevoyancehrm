package com.main.prevoyancehrm.service.serviceInterface;

public interface EmailService {
    boolean welcomeEmail(String email,String name,String position,String mobileNo);
    boolean aprovalEmail(String to,String subject,String body);
    boolean sendEmail(String to,String subject,String body);
}
