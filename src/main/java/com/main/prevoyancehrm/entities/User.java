package com.main.prevoyancehrm.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.main.prevoyancehrm.constants.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String password;
    private String mobileNo;
    private String emgMobileNo;
    private String officialEmail;
    private String dob;
    private String adharNo;
    private String employeeId;
    private String registerDate;
    private String loginDate;

    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String presentAddress;
    private String permanentAddress;
    
    private boolean employee;
    private boolean approved;
    private boolean active;
    
    @Enumerated(EnumType.STRING)
    private Role role = Role.CANDIDATE;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EducationDetail> educationDetails;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceDetail> experienceDetail;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfessionalDetail professionalDetail;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private BankDetails bankDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Salary salary;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalanceLeaves> balanceLeaves;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.getRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

}
