package com.main.prevoyancehrm.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int maxAllowed;
    private String detail;


    private boolean isDelete=false;
    private boolean isActive=false;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime modifyAt=LocalDateTime.now();
    private LocalDateTime deleteAt;
}
