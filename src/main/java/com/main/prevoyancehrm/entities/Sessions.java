package com.main.prevoyancehrm.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sessionId;

    private String token;
    private LocalDateTime issueAt;
    private LocalDateTime logoutAt;
    private long expiration;

    private boolean isDelete = false;
    private boolean isActive = true;
    
    private LocalDateTime deleteDate;
    

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
