package com.main.prevoyancehrm.service.serviceLogic;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.dto.RequestDto.BankDetailRequestDto;
import com.main.prevoyancehrm.dto.RequestDto.EducationDetailRequestDTO;
import com.main.prevoyancehrm.dto.RequestDto.EmployeeRequestDto;
import com.main.prevoyancehrm.dto.RequestDto.ExperienceDetailRequestDto;
import com.main.prevoyancehrm.dto.RequestDto.ProfessionalDetailRequestDTO;
import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.entities.EducationDetail;
import com.main.prevoyancehrm.entities.ExperienceDetail;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.helper.DateTimeFormat;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.EducationDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ExperienceDetailsServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.SalaryServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@Service
public class EmployeeServiceLogic {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ProfessionalDetailServiceImpl professionalDetailServiceImpl;

    @Autowired
    private EducationDetailServiceImpl educationDetailServiceImpl;

    @Autowired
    private BankDetailServiceImpl bankDetailServiceImpl;

    @Autowired
    private ExperienceDetailsServiceImpl experienceDetailsServiceImpl;

    @Autowired
    private SalaryServiceImpl salaryServiceImpl;



    public User addEmployee(EmployeeRequestDto employee) throws Exception{
        User user = new User();

        if (employee.getPersonalDetail() != null) {
            user.setGender(employee.getPersonalDetail().getGender());
            user.setEmail(employee.getPersonalDetail().getEmail());
            user.setFirstName(employee.getPersonalDetail().getFirstName());
            user.setLastName(employee.getPersonalDetail().getLastName()); // Added
            user.setMobileNo(employee.getPersonalDetail().getMobileNo());
            user.setEmgMobileNo(employee.getPersonalDetail().getEmgMobileNo());
            user.setOfficialEmail(employee.getPersonalDetail().getOfficialEmail()); // Added
            user.setDob(employee.getPersonalDetail().getDob()); // Added
            user.setAdharNo(employee.getPersonalDetail().getAdharNo()); // Added
            user.setImage(employee.getPersonalDetail().getImage());
            user.setPresentAddress(employee.getPersonalDetail().getPresentAddress());
            user.setPermanentAddress(employee.getPersonalDetail().getPermanentAddress());
            user.setRegisterDate(DateTimeFormat.format(LocalDateTime.now()));
        }
        
        user.setActive(true);
        user.setApproved(false);
        user = this.userServiceImpl.registerUser(user);

        BankDetails bankDetails = new BankDetails();
        if (employee.getBankDetail() != null) {
            BankDetailRequestDto bankDetailRequestDto = employee.getBankDetail();
            bankDetails.setBankName(bankDetailRequestDto.getBankName());
            bankDetails.setBankAccountNo(bankDetailRequestDto.getBankAccountNo());
            bankDetails.setIfscCode(bankDetailRequestDto.getIfscCode());
            bankDetails.setPanNo(bankDetailRequestDto.getPanNo());
            bankDetails.setUanNo(bankDetailRequestDto.getUanNo());
            bankDetails.setUser(user);
            this.bankDetailServiceImpl.addBankDetails(bankDetails);
            
        }
        
        ProfessionalDetail pDetail = new ProfessionalDetail();
        if (employee.getProfessionalDetails() != null) {
            ProfessionalDetailRequestDTO professionalDetail = employee.getProfessionalDetails();
            pDetail.setTotalExperience(professionalDetail.getTotalExperience());
            pDetail.setLocation(professionalDetail.getLocation());
            pDetail.setHireSource(professionalDetail.getHireSource());
            pDetail.setPosition(professionalDetail.getPosition());
            pDetail.setDepartment(professionalDetail.getDepartment());
            pDetail.setSkills(professionalDetail.getSkills());
            pDetail.setHighestQualification(professionalDetail.getHighestQualification());
            pDetail.setJoiningDate(professionalDetail.getJoiningDate());
            pDetail.setAdditionalInfo(professionalDetail.getAdditionalInfo());
            pDetail.setOfferLetter(professionalDetail.getOfferLetter());
            pDetail.setUser(user);
            this.professionalDetailServiceImpl.addProfessionalDetail(pDetail);
        }
        if(employee.getProfessionalDetails().getCurrentSalary()!=0){
            Salary salary = new Salary();
            salary.setGrossSalary(employee.getProfessionalDetails().getCurrentSalary());
            salary.setUser(user);
            this.salaryServiceImpl.addSalary(salary);

        }

        if (employee.getEducationDetails() != null) {
            for (EducationDetailRequestDTO educationDto : employee.getEducationDetails()) {
                EducationDetail educationDetail = new EducationDetail();
                educationDetail.setDegree(educationDto.getDegree());
                educationDetail.setCollege(educationDto.getCollege());
                educationDetail.setField(educationDto.getField());
                educationDetail.setPassingYear(educationDto.getPassingYear());
                educationDetail.setMarksInPercent(educationDto.getMarksInPercent());
                educationDetail.setAdditionalNote(educationDto.getAdditionalNote());
                educationDetail.setUser(user);
                this.educationDetailServiceImpl.addEducationDetail(educationDetail);
            }
        }

        if (employee.getExperienceDetails() != null) {
            for (ExperienceDetailRequestDto experienceDetailRequestDto : employee.getExperienceDetails()) {
                ExperienceDetail experienceDetail = new ExperienceDetail();
                experienceDetail.setCompanyName(experienceDetailRequestDto.getCompanyName());
                experienceDetail.setDesignation(experienceDetailRequestDto.getDesignation());
                experienceDetail.setDuration(experienceDetailRequestDto.getDuration());
                experienceDetail.setAnnualCTC(experienceDetailRequestDto.getAnnualCTC());
                experienceDetail.setOfferLetter(experienceDetailRequestDto.getOfferLetter());
                experienceDetail.setSalarySlip(experienceDetailRequestDto.getSalarySlip());
                experienceDetail.setReasonOfLeaving(experienceDetailRequestDto.getReasonOfLeaving());
                experienceDetail.setUser(user);
                this.experienceDetailsServiceImpl.addExperienceDetail(experienceDetail);
            }
        }
          
        return user;
    }
    
}
