package com.main.prevoyancehrm.service.serviceImpl;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.OtpEntry;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.repository.OtpEntryRepo;
import com.main.prevoyancehrm.service.serviceInterface.OptService;

@Service
public class OtpServiceImpl implements OptService{
    @Autowired
    private OtpEntryRepo otpEntryRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public boolean varifyOtp(String email, String otp) {
        OtpEntry otpEntry = this.otpEntryRepo.findByEmail(email);
        return otpEntry!=null && otpEntry.getOtp().equals(otp) && !otpEntry.isExpired();
    }

    @Override
    public boolean varifySession(String email, String otp) {
        OtpEntry otpEntry = this.otpEntryRepo.findByEmail(email);
        return otpEntry!=null && otpEntry.getOtp().equals(otp) && !otpEntry.isSessionExpired();
    }

    @Override
    public String sendOtp(String email) {
        otpEntryRepo.deleteByEmail(email);
        OtpEntry otpEntry = new OtpEntry();
        otpEntry.setEmail(email);
        otpEntry.setOtp(generateOtp());
        otpEntry.setExpirationTime(LocalDateTime.now().plusSeconds(45));
        otpEntry.setSessionExpired(LocalDateTime.now().plusMinutes(2));
        this.otpEntryRepo.save(otpEntry);
        // send mail with otp
        return otpEntry.getOtp();
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) throws Exception {
        if(!varifySession(email, otp)){
            return false;
        }
        User user = userServiceImpl.getUserByEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setModifyAt(LocalDateTime.now());
        this.userServiceImpl.updateUser(user);
        this.otpEntryRepo.deleteByEmail(email);
        return true;
    }
}
