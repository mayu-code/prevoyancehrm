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
import lombok.Data;



@Entity
@Data
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String email;
    private String password;
    private String name;
    private String fathersName;
    private String mobileNo;
    private String emgMobileNo;

    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String presentAddress;
    private String permanentAddress;
    private String bankAccountNo;
    private String ifscCode;
    private String possition;

    private boolean approved;
    private boolean active;
    
    @Enumerated(EnumType.STRING)
    private Role role = Role.EMPLOYEE;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<EducationDetail> educationDetails;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<ProfessionalDetail> ProfessionalDetail;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.getRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

}
