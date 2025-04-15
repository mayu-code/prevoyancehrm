package com.main.prevoyancehrm.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class EducationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String degree;
    private String college;
    private String field;
    private String passingYear;
    private String marksInPercent;
    private String additionalNote;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user; 

    private boolean isDelete=false;
    private boolean isActive=false;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime modifyAt=LocalDateTime.now();
    private LocalDateTime deleteAt;
}
