package com.main.prevoyancehrm.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ProfessionalDetail {
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String totalExperience;
    private String location;
    private String hireSource;
    private String position;
    private String department;
    private String skills;
    private String highestQualification;
    private String joiningDate;

    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo;

    @Column(columnDefinition = "LONGTEXT")
    private String offerLetter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    private boolean isDelete=false;
    private boolean isActive=false;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime modifyAt=LocalDateTime.now();
    private LocalDateTime deleteAt;
}
