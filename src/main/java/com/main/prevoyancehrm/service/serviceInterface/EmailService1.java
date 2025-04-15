package com.main.prevoyancehrm.service.serviceInterface;

public interface EmailService1 {
    void welcomeEmail(String email,String name,String position,String mobileNo);
    void aprovalEmail(String to,String subject,String body);
    void sendEmail(String to,String subject,String body);
}
