package com.main.prevoyancehrm.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OtpEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String otp;
    private LocalDateTime expirationTime;
    private LocalDateTime sessionExpired;

    public boolean isSessionExpired(){
        return LocalDateTime.now().isAfter(sessionExpired);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
