package com.main.prevoyancehrm.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Salary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double basicSalary;
    private double hra;
    private double da;
    private double medicalAllowance;
    private double travelAllowance;
    private double providentFund;
    private double professionalTax;
    private double incomeTax;
    private double grossSalary;
    private double netSalary;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "salary",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SalarySlip> salarySlips;
}
