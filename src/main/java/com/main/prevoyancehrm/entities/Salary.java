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
    private double professionalTax=200;
    private double incomeTax;
    private double grossSalary;
    private double netSalary;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore

    private User user;

    @OneToMany(mappedBy = "salary",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<SalarySlip> salarySlips;

    public void setGrossSalary(double grossSalary) {
        this.basicSalary = grossSalary * 0.50;
        this.hra = grossSalary * 0.25;
        this.da = grossSalary * 0.20;
        this.medicalAllowance = grossSalary * 0.025;
        this.travelAllowance = grossSalary * 0.025;
        this.netSalary = grossSalary - this.professionalTax;
        this.grossSalary = grossSalary;
    }
}
