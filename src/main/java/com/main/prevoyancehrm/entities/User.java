package com.main.prevoyancehrm.entities;

import java.time.LocalDateTime;
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
    private String id;

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

    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String presentAddress;
    private String permanentAddress;
    
    private boolean employee;
    private boolean approved;

    private boolean isDelete=false;
    private boolean isActive=false;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime modifyAt=LocalDateTime.now();
    private LocalDateTime deleteAt;
    
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
