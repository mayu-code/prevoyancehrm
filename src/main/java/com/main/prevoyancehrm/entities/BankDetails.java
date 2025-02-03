package com.main.prevoyancehrm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String bankName;
    private String bankAccountNo;
    private String ifscCode;
    private String panNo;
    private String uanNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

}
