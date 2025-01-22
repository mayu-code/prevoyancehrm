package com.main.prevoyancehrm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String passingYear;
    private String marksInPercent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user; 
}
