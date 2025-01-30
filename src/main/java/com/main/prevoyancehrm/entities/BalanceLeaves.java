package com.main.prevoyancehrm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class BalanceLeaves {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float paidLeaves=18f;
    private float balaceLeaves=18f;
    private float leavesTaken=0;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
