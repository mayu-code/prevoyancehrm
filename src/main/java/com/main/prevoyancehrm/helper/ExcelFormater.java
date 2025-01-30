package com.main.prevoyancehrm.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@Service
public class ExcelFormater {

    @Autowired
    private  UserServiceImpl userServiceImpl;

    @Autowired
    private  ProfessionalDetailServiceImpl professionalDetailServiceImpl;

    @Autowired
    private  BankDetailServiceImpl bankDetailsServiceImpl;

    public byte[] exportEmployee(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");
    
        // // Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"id", "First Name", "Last Name", "Email", "Official Email", "Mobile No", "Emergency Mobile No",
            "dob", "Adhar No", "Present Address", "Permanent Address", "Department", "Position", 
            "Total Experience", "Skills", "Highest Qualification", "Joining Date", 
            "Current Salary", "Location", "Bank Name", "Account No", "IFSC Code", "Pan No", "UAN No"};
    
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowIndex = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName() != null ? user.getFirstName() : " ");
            row.createCell(2).setCellValue(user.getLastName() != null ? user.getLastName() : " ");
            row.createCell(3).setCellValue(user.getEmail() != null ? user.getEmail() : " ");
            row.createCell(4).setCellValue(user.getOfficialEmail() != null ? user.getOfficialEmail() : " ");
            row.createCell(5).setCellValue(user.getMobileNo() != null ? user.getMobileNo() : " ");
            row.createCell(6).setCellValue(user.getEmgMobileNo() != null ? user.getEmgMobileNo() : " ");
            row.createCell(7).setCellValue(user.getDob() != null ? user.getDob() : " ");
            row.createCell(8).setCellValue(user.getAdharNo() != null ? user.getAdharNo() : " ");
            row.createCell(9).setCellValue(user.getPresentAddress() != null ? user.getPresentAddress() : " ");
            row.createCell(10).setCellValue(user.getPermanentAddress() != null ? user.getPermanentAddress() : " ");

            if (user.getProfessionalDetail() != null) {
                row.createCell(11).setCellValue(user.getProfessionalDetail().getDepartment() != null ? user.getProfessionalDetail().getDepartment() : " ");
                row.createCell(12).setCellValue(user.getProfessionalDetail().getPosition() != null ? user.getProfessionalDetail().getPosition() : " ");
                row.createCell(13).setCellValue(user.getProfessionalDetail().getTotalExperience() != null ? user.getProfessionalDetail().getTotalExperience() : " ");
                row.createCell(14).setCellValue(user.getProfessionalDetail().getSkills() != null ? user.getProfessionalDetail().getSkills() : " ");
                row.createCell(15).setCellValue(user.getProfessionalDetail().getHighestQualification() != null ? user.getProfessionalDetail().getHighestQualification() : " ");
                row.createCell(16).setCellValue(user.getProfessionalDetail().getJoiningDate() != null ? user.getProfessionalDetail().getJoiningDate().toString() : " ");
                row.createCell(17).setCellValue(user.getProfessionalDetail().getCurrentSalary());
                row.createCell(18).setCellValue(user.getProfessionalDetail().getLocation() != null ? user.getProfessionalDetail().getLocation() : " ");
            }

            if (user.getBankDetails() != null) {
                row.createCell(19).setCellValue(user.getBankDetails().getBankName() != null ? user.getBankDetails().getBankName() : " ");
                row.createCell(20).setCellValue(user.getBankDetails().getBankAccountNo() != null ? user.getBankDetails().getBankAccountNo() : " ");
                row.createCell(21).setCellValue(user.getBankDetails().getIfscCode() != null ? user.getBankDetails().getIfscCode() : " ");
                row.createCell(22).setCellValue(user.getBankDetails().getPanNo() != null ? user.getBankDetails().getPanNo() : " ");
                row.createCell(23).setCellValue(user.getBankDetails().getUanNo() != null ? user.getBankDetails().getUanNo() : " ");
            }
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
    
        return outputStream.toByteArray();
    } 
    
    public  void importCandidates(MultipartFile file) throws IOException{
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
 
        Iterator<Row> rowIterator = sheet.iterator();
        if(rowIterator.hasNext()){
             rowIterator.next();
        }
 
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            User user = new User();
            ProfessionalDetail professionalDetail = new ProfessionalDetail();
            BankDetails bankDetails = new BankDetails();
 
            user.setFirstName(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);   
            user.setLastName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);   
            user.setGender(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);  
            user.setEmail(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);  
            user.setOfficialEmail(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null); 
            user.setMobileNo(row.getCell(5) != null ? String.valueOf(row.getCell(5).getNumericCellValue()) :null ); 
            user.setEmgMobileNo(row.getCell(6) != null ? String.valueOf(row.getCell(6).getNumericCellValue()) :null);
            user.setAdharNo(row.getCell(7) != null ? String.valueOf(row.getCell(7).getNumericCellValue()) :null );
            user.setDob(row.getCell(8) != null ? String.valueOf(row.getCell(8).getNumericCellValue()) :null);
            user.setPresentAddress(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null);
            user.setPermanentAddress(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null);

            user = this.userServiceImpl.registerUser(user);
    
            bankDetails.setBankName(row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null); 
            bankDetails.setBankAccountNo(row.getCell(12) != null ? String.valueOf(row.getCell(12).getNumericCellValue()) :null); 
            bankDetails.setIfscCode(row.getCell(13) != null ? row.getCell(13).getStringCellValue() : null); 
            bankDetails.setPanNo(row.getCell(14) != null ? row.getCell(14).getStringCellValue():null); 
            bankDetails.setUanNo(row.getCell(15) != null ? String.valueOf(row.getCell(15).getNumericCellValue()) :null); 

            bankDetails.setUser(user);
            this.bankDetailsServiceImpl.addBankDetails(bankDetails);

            professionalDetail.setTotalExperience(row.getCell(16) != null ? row.getCell(16).getStringCellValue() : null); 
            professionalDetail.setLocation(row.getCell(17) != null ? row.getCell(17).getStringCellValue() : null); 
            professionalDetail.setHireSource(row.getCell(18) != null ? row.getCell(18).getStringCellValue() : null); 
            professionalDetail.setPosition(row.getCell(19) != null ? row.getCell(19).getStringCellValue() : null); 
            professionalDetail.setDepartment(row.getCell(20) != null ? row.getCell(20).getStringCellValue() : null); 
            professionalDetail.setSkills(row.getCell(21) != null ? row.getCell(21).getStringCellValue() : null); 
            professionalDetail.setHighestQualification(row.getCell(22) != null ? row.getCell(22).getStringCellValue() : null); 
            professionalDetail.setCurrentSalary(row.getCell(23) != null ? (double)row.getCell(23).getNumericCellValue() : 0); 
            professionalDetail.setJoiningDate(row.getCell(24) != null ? String.valueOf(row.getCell(12).getNumericCellValue()) :null); 
            professionalDetail.setAdditionalInfo(row.getCell(25) != null ? row.getCell(25).getStringCellValue() : null);
            
            professionalDetail.setUser(user);
            this.professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);
        }
 
        workbook.close();
        return;
    }

    public void importEmployee(MultipartFile file) throws IOException{
       Workbook workbook = new XSSFWorkbook(file.getInputStream());
       Sheet sheet = workbook.getSheetAt(0);

       Iterator<Row> rowIterator = sheet.iterator();
       if(rowIterator.hasNext()){
            rowIterator.next();
       }

       while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            User user = new User();
            ProfessionalDetail professionalDetail = new ProfessionalDetail();
            BankDetails bankDetails = new BankDetails();

            user.setFirstName(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);   
            user.setLastName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);   
            user.setGender(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);  
            user.setEmail(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);  
            user.setOfficialEmail(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null); 
            user.setMobileNo(row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null); 
            user.setEmgMobileNo(row.getCell(6) != null ? row.getCell(6).getStringCellValue() : null);
            user.setDob(row.getCell(7) != null ? row.getCell(7).getStringCellValue() : null);
            user.setAdharNo(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null);
            user.setPresentAddress(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null);
            user.setPermanentAddress(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null);

            bankDetails.setBankName(row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null); 
            bankDetails.setBankAccountNo(row.getCell(12) != null ? row.getCell(12).getStringCellValue() : null); 
            bankDetails.setIfscCode(row.getCell(13) != null ? row.getCell(13).getStringCellValue() : null); 
            bankDetails.setPanNo(row.getCell(14) != null ? row.getCell(14).getStringCellValue() : null); 
            bankDetails.setUanNo(row.getCell(15) != null ? row.getCell(15).getStringCellValue() : null); 

            professionalDetail.setTotalExperience(row.getCell(16) != null ? row.getCell(16).getStringCellValue() : null); 
            professionalDetail.setLocation(row.getCell(17) != null ? row.getCell(17).getStringCellValue() : null); 
            professionalDetail.setHireSource(row.getCell(18) != null ? row.getCell(18).getStringCellValue() : null); 
            professionalDetail.setPosition(row.getCell(19) != null ? row.getCell(19).getStringCellValue() : null); 
            professionalDetail.setDepartment(row.getCell(20) != null ? row.getCell(20).getStringCellValue() : null); 
            professionalDetail.setSkills(row.getCell(21) != null ? row.getCell(21).getStringCellValue() : null); 
            professionalDetail.setHighestQualification(row.getCell(22) != null ? row.getCell(22).getStringCellValue() : null); 
            professionalDetail.setCurrentSalary(row.getCell(23) != null ? row.getCell(23).getNumericCellValue() : 0); 
            professionalDetail.setJoiningDate(row.getCell(24) != null ? row.getCell(24).getStringCellValue() : null); 
            professionalDetail.setAdditionalInfo(row.getCell(25) != null ? row.getCell(25).getStringCellValue() : null); 
       }

       workbook.close();
       return;

    }
}

