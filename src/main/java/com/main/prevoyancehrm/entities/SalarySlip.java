package com.main.prevoyancehrm.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class SalarySlip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate date;
    
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_id")
    @JsonIgnore
    private Salary salary;
}
