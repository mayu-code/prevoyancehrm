package com.main.prevoyancehrm.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTaken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float leavesTaken;
    private LocalDate fromDate;
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name = "balanceLeaves_id")
    @JsonIgnore
    private BalanceLeaves balanceLeaves;

    
    private boolean isDelete=false;
    private boolean isActive=false;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime modifyAt=LocalDateTime.now();
    private LocalDateTime deleteAt;
}
