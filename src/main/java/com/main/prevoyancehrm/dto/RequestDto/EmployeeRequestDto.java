package com.main.prevoyancehrm.dto.RequestDto;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeRequestDto {
    private PersonaInfo personaInfo;
    private List<EducationDetailRequestDTO> educationDetails;
    private List<ProfessionalDetailRequestDTO> professionalDetails;
}
