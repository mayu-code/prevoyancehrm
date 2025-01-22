package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.helper.EmailFormater;
import com.main.prevoyancehrm.service.serviceInterface.EmailService;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private  JavaMailSender mailSender;

    
    @Override
    @Async("taskExecutor")
    @Transactional
    public  void welcomeEmail(String email,String name,String position,String mobileNo ) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Welcome In Prevoyance");
            message.setContent(EmailFormater.generateWelcomeEmail(email,name,position,mobileNo), "text/html"); 
            mailSender.send(message);
            return ;
        }catch(Exception e){
            return ;
        }
    }

    @Override
    public void aprovalEmail(String to, String subject, String body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aprovalEmail'");
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmail'");
    }
    
}
