package com.main.prevoyancehrm.service.serviceLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.dto.RequestDto.EducationDetailRequestDTO;
import com.main.prevoyancehrm.dto.RequestDto.EmployeeRequestDto;
import com.main.prevoyancehrm.entities.EducationDetail;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.EducationDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@Service
public class EmployeeServiceLogic {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ProfessionalDetailServiceImpl professionalDetailServiceImpl;

    @Autowired
    private EducationDetailServiceImpl educationDetailServiceImpl;


    public User addEmployee(EmployeeRequestDto employee){
        User user = new User();

        if (employee.getPersonaInfo() != null) {
            user.setEmail(employee.getPersonaInfo().getEmail());
            user.setName(employee.getPersonaInfo().getName());
            user.setFathersName(employee.getPersonaInfo().getFathersName());
            user.setMobileNo(employee.getPersonaInfo().getMobileNo());
            user.setEmgMobileNo(employee.getPersonaInfo().getEmgMobileNo());
            user.setImage(employee.getPersonaInfo().getImage());
            user.setPresentAddress(employee.getPersonaInfo().getPresentAddress());
            user.setPermanentAddress(employee.getPersonaInfo().getPermanentAddress());
            user.setBankAccountNo(employee.getPersonaInfo().getBankAccountNo());
            user.setIfscCode(employee.getPersonaInfo().getIfscCode());
            user.setPossition(employee.getPersonaInfo().getPossition());
        }

        user.setActive(true);
        user.setApproved(false);

        user = this.userServiceImpl.registerUser(user);
        if (employee.getEducationDetails() != null) {
            for (EducationDetailRequestDTO educationDto : employee.getEducationDetails()) {
                EducationDetail educationDetail = new EducationDetail();
                educationDetail.setDegree(educationDto.getDegree());
                educationDetail.setCollge(educationDto.getCollge());
                educationDetail.setPassingYear(educationDto.getPassingYear());
                educationDetail.setMarksInPercent(educationDto.getMarksInPercent());
                educationDetail.setUser(user);
                this.educationDetailServiceImpl.addEducationDetail(educationDetail);
            }
        }
        if (employee.getProfessionalDetails() != null) {
            for (var professionalDto : employee.getProfessionalDetails()) {
                ProfessionalDetail professionalDetail = new ProfessionalDetail();
                professionalDetail.setOrganizationName(professionalDto.getOrganizationName());
                professionalDetail.setDesignation(professionalDto.getDesignation());
                professionalDetail.setDuration(professionalDto.getDuration());
                professionalDetail.setAnnualCTC(professionalDto.getAnnualCTC());
                professionalDetail.setReasonOfLeaving(professionalDto.getReasonOfLeaving());
                professionalDetail.setUser(user);
                this.professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);
            }
        }
        return user;
    }
    
}
