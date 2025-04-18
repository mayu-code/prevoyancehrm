package com.main.prevoyancehrm.dto.RequestDto;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeRequestDto {
    private PersonalDetail personalDetail;
    private List<EducationDetailRequestDTO> educationDetails;
    private ProfessionalDetailRequestDTO professionalDetails;
    private List<BankDetailRequestDto> bankDetail;
    private List<ExperienceDetailRequestDto> experienceDetails;
}
