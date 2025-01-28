package com.main.prevoyancehrm.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.main.prevoyancehrm.entities.User;

public class ExcelFormater {

    public static byte[] exportEmployee(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");
    
        // Define header row
        String[] headers = {"id", "First Name", "Last Name", "Email", "Official Email", "Mobile No", "Emergency Mobile No",
            "dob", "Adhar No", "Present Address", "Permanent Address", "Department", "Position", 
            "Total Experience", "Skills", "Highest Qualification", "Joining Date", 
            "Current Salary", "Location", "Bank Name", "Account No", "IFSC Code", "Pan No", "UAN No"};
    
        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    
        // Populate data rows
        int rowIndex = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowIndex++);
    
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getEmail());
            row.createCell(4).setCellValue(user.getOfficialEmail());
            row.createCell(5).setCellValue(user.getMobileNo());
            row.createCell(6).setCellValue(user.getEmgMobileNo());
            row.createCell(7).setCellValue(user.getDob());
            row.createCell(8).setCellValue(user.getAdharNo());
            row.createCell(9).setCellValue(user.getPresentAddress());
            row.createCell(10).setCellValue(user.getPermanentAddress());
            
           if(user.getProfessionalDetail()!=null){
            row.createCell(11).setCellValue(user.getProfessionalDetail().getDepartment());
            row.createCell(12).setCellValue(user.getProfessionalDetail().getPosition());
            row.createCell(13).setCellValue(user.getProfessionalDetail().getTotalExperience());
            row.createCell(14).setCellValue(user.getProfessionalDetail().getSkills());
            row.createCell(15).setCellValue(user.getProfessionalDetail().getHighestQualification());
            row.createCell(16).setCellValue(user.getProfessionalDetail().getJoiningDate());
            row.createCell(17).setCellValue(user.getProfessionalDetail().getCurrentSalary());
            row.createCell(18).setCellValue(user.getProfessionalDetail().getLocation());
           }
            if(user.getBankDetails()!=null){
                row.createCell(19).setCellValue(user.getBankDetails().getBankName());
                row.createCell(20).setCellValue(user.getBankDetails().getBankAccountNo());
                row.createCell(21).setCellValue(user.getBankDetails().getIfscCode());
                row.createCell(22).setCellValue(user.getBankDetails().getPanNo());
                row.createCell(23).setCellValue(user.getBankDetails().getUanNo());
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
}
