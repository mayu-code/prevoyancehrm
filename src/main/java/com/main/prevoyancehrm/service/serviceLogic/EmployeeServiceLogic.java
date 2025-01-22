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

        if (employee.getPersonalDetail() != null) {
            user.setEmail(employee.getPersonalDetail().getEmail());
            user.setName(employee.getPersonalDetail().getName());
            user.setFathersName(employee.getPersonalDetail().getFathersName());
            user.setMobileNo(employee.getPersonalDetail().getMobileNo());
            user.setEmgMobileNo(employee.getPersonalDetail().getEmgMobileNo());
            user.setImage(employee.getPersonalDetail().getImage());
            user.setPresentAddress(employee.getPersonalDetail().getPresentAddress());
            user.setPermanentAddress(employee.getPersonalDetail().getPermanentAddress());
            user.setBankAccountNo(employee.getPersonalDetail().getBankAccountNo());
            user.setIfscCode(employee.getPersonalDetail().getIfscCode());
            user.setPossition(employee.getPersonalDetail().getPossition());
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
