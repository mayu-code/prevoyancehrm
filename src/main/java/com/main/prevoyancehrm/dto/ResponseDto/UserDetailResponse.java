package com.main.prevoyancehrm.dto.ResponseDto;

import java.util.List;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.entities.ExperienceDetail;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.Salary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emgMobileNo;
    private String officialEmail;
    private String dob;
    private String adharNo;

    private String image;
    private String presentAddress;
    private String permanentAddress;

    private Role role ;

    private List<EducationDetailResponse> educationDetails;

    private List<ExperienceDetail> experienceDetail;

    private ProfessionalDetail professionalDetail;

    private List<BankDetails> bankDetails;

    private Salary salary;
}
