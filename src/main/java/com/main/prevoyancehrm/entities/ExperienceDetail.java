package com.main.prevoyancehrm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ExperienceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String companyName;
    private String designation;
    private String duration;
    private String annualCTC;

    @Column(columnDefinition = "LONGTEXT")
    private String offerLetter;

    @Column(columnDefinition = "LONGTEXT")
    private String salarySlip;

    @Column(columnDefinition = "LONGTEXT")
    private String reasonOfLeaving;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user; 
}
