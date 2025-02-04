package com.main.prevoyancehrm.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.SalaryServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@Service
public class ExcelFormater {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ProfessionalDetailServiceImpl professionalDetailServiceImpl;

    @Autowired
    private BankDetailServiceImpl bankDetailsServiceImpl;

    @Autowired
    private SalaryServiceImpl salaryServiceImpl;

    public byte[] exportEmployee(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // // Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Employee id", "First Name", "Last Name", "Email", "Official Email", "Mobile No",
                "Emergency Mobile No",
                "dob", "Adhar No", "Present Address", "Permanent Address", "Department", "Position",
                "Total Experience", "Skills", "Highest Qualification", "Joining Date",
                "Current Salary", "Location", "Bank Name", "Account No", "IFSC Code", "Pan No", "UAN No" };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowIndex = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(user.getEmployeeId());
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
                row.createCell(11)
                        .setCellValue(user.getProfessionalDetail().getDepartment() != null
                                ? user.getProfessionalDetail().getDepartment()
                                : " ");
                row.createCell(12)
                        .setCellValue(user.getProfessionalDetail().getPosition() != null
                                ? user.getProfessionalDetail().getPosition()
                                : " ");
                row.createCell(13)
                        .setCellValue(user.getProfessionalDetail().getTotalExperience() != null
                                ? user.getProfessionalDetail().getTotalExperience()
                                : " ");
                row.createCell(14)
                        .setCellValue(user.getProfessionalDetail().getSkills() != null
                                ? user.getProfessionalDetail().getSkills()
                                : " ");
                row.createCell(15)
                        .setCellValue(user.getProfessionalDetail().getHighestQualification() != null
                                ? user.getProfessionalDetail().getHighestQualification()
                                : " ");
                row.createCell(16)
                        .setCellValue(user.getProfessionalDetail().getJoiningDate() != null
                                ? user.getProfessionalDetail().getJoiningDate().toString()
                                : " ");
                row.createCell(17).setCellValue(user.getSalary().getGrossSalary());
                row.createCell(18)
                        .setCellValue(user.getProfessionalDetail().getLocation() != null
                                ? user.getProfessionalDetail().getLocation()
                                : " ");
            }

            if (user.getBankDetails() != null) {
                row.createCell(19).setCellValue(
                        user.getBankDetails().getBankName() != null ? user.getBankDetails().getBankName() : " ");
                row.createCell(20)
                        .setCellValue(user.getBankDetails().getBankAccountNo() != null
                                ? user.getBankDetails().getBankAccountNo()
                                : " ");
                row.createCell(21).setCellValue(
                        user.getBankDetails().getIfscCode() != null ? user.getBankDetails().getIfscCode() : " ");
                row.createCell(22).setCellValue(
                        user.getBankDetails().getPanNo() != null ? user.getBankDetails().getPanNo() : " ");
                row.createCell(23).setCellValue(
                        user.getBankDetails().getUanNo() != null ? user.getBankDetails().getUanNo() : " ");
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

    public List<String> importCandidates(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        List<String> errorEmails = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            User user = new User();
            ProfessionalDetail professionalDetail = new ProfessionalDetail();
            BankDetails bankDetails = new BankDetails();
            Salary salary = new Salary();

            User user2 = this.userServiceImpl.getUserByEmail(row.getCell(3).getStringCellValue());
            if (user2 != null) {
                continue;
            }

            try {
                user.setFirstName(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);
                user.setLastName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);
                user.setGender(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);
                user.setEmail(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);
                user.setOfficialEmail(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null);
                user.setMobileNo(
                        row.getCell(5) != null ? String.valueOf((long) row.getCell(5).getNumericCellValue()) : null);
                user.setEmgMobileNo(
                        row.getCell(6) != null ? String.valueOf((long) row.getCell(6).getNumericCellValue()) : null);
                user.setAdharNo(
                        row.getCell(7) != null ? String.valueOf((long) row.getCell(7).getNumericCellValue()) : null);
                user.setDob(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null);
                user.setPresentAddress(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null);
                user.setPermanentAddress(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null);

                bankDetails.setBankName(row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null);
                bankDetails.setBankAccountNo(
                        row.getCell(12) != null ? String.valueOf((long) row.getCell(12).getNumericCellValue()) : null);
                bankDetails.setIfscCode(row.getCell(13) != null ? row.getCell(13).getStringCellValue() : null);
                bankDetails.setPanNo(row.getCell(14) != null ? row.getCell(14).getStringCellValue() : null);
                bankDetails.setUanNo(
                        row.getCell(15) != null ? String.valueOf((long) row.getCell(15).getNumericCellValue()) : null);

                professionalDetail
                        .setTotalExperience(row.getCell(16) != null ? row.getCell(16).getStringCellValue() : null);
                professionalDetail.setLocation(row.getCell(17) != null ? row.getCell(17).getStringCellValue() : null);
                professionalDetail.setHireSource(row.getCell(18) != null ? row.getCell(18).getStringCellValue() : null);
                professionalDetail.setPosition(row.getCell(19) != null ? row.getCell(19).getStringCellValue() : null);
                professionalDetail.setDepartment(row.getCell(20) != null ? row.getCell(20).getStringCellValue() : null);
                professionalDetail.setSkills(row.getCell(21) != null ? row.getCell(21).getStringCellValue() : null);
                professionalDetail
                        .setHighestQualification(row.getCell(22) != null ? row.getCell(22).getStringCellValue() : null);
                professionalDetail
                        .setJoiningDate(row.getCell(24) != null ? row.getCell(24).getStringCellValue() : null);
                professionalDetail
                        .setAdditionalInfo(row.getCell(25) != null ? row.getCell(25).getStringCellValue() : null);

                double currentSalary = (double) row.getCell(23).getNumericCellValue();

                user = this.userServiceImpl.registerUser(user);
                salary.setGrossSalary(currentSalary);
                salary.setUser(user);
                this.salaryServiceImpl.addSalary(salary);
                bankDetails.setUser(user);
                this.bankDetailsServiceImpl.addBankDetails(bankDetails);

                professionalDetail.setUser(user);
                this.professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);
            } catch (Exception e) {
                errorEmails.add(row.getCell(3).getStringCellValue());
                continue;
            }
        }

        workbook.close();
        return errorEmails;
    }

    public List<String> importEmployee(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        List<String> errorEmails = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            User user = new User();
            ProfessionalDetail professionalDetail = new ProfessionalDetail();
            BankDetails bankDetails = new BankDetails();
            Salary salary = new Salary();

            User user2 = this.userServiceImpl.getUserByEmail(row.getCell(3).getStringCellValue());
            if (user2 != null) {
                continue;
            }

            try {
                user.setEmployeeId(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);
                user.setFirstName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);
                user.setLastName(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);
                user.setGender(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);
                user.setEmail(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null);
                user.setOfficialEmail(row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null);
                user.setMobileNo(
                        row.getCell(6) != null ? String.valueOf((long) row.getCell(6).getNumericCellValue()) : null);
                user.setEmgMobileNo(
                        row.getCell(7) != null ? String.valueOf((long) row.getCell(7).getNumericCellValue()) : null);
                user.setAdharNo(
                        row.getCell(8) != null ? String.valueOf((long) row.getCell(8).getNumericCellValue()) : null);
                user.setDob(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null);
                user.setPresentAddress(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null);
                user.setPermanentAddress(row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null);

                bankDetails.setBankName(row.getCell(12) != null ? row.getCell(12).getStringCellValue() : null);
                bankDetails.setBankAccountNo(row.getCell(13) != null ?String.valueOf((long) row.getCell(13).getNumericCellValue()) : null);
                bankDetails.setIfscCode(row.getCell(14) != null ? row.getCell(14).getStringCellValue() : null);
                bankDetails.setPanNo(row.getCell(15) != null ? row.getCell(15).getStringCellValue() : null);
                bankDetails.setUanNo(row.getCell(16) != null ? String.valueOf((long) row.getCell(16).getNumericCellValue()) : null);

                professionalDetail
                        .setTotalExperience(row.getCell(17) != null ? row.getCell(17).getStringCellValue() : null);
                professionalDetail.setLocation(row.getCell(18) != null ? row.getCell(18).getStringCellValue() : null);
                professionalDetail.setHireSource(row.getCell(19) != null ? row.getCell(19).getStringCellValue() : null);
                professionalDetail.setPosition(row.getCell(20) != null ? row.getCell(20).getStringCellValue() : null);
                professionalDetail.setDepartment(row.getCell(21) != null ? row.getCell(21).getStringCellValue() : null);
                professionalDetail.setSkills(row.getCell(22) != null ? row.getCell(22).getStringCellValue() : null);
                professionalDetail
                        .setHighestQualification(row.getCell(23) != null ? row.getCell(23).getStringCellValue() : null);

                professionalDetail
                        .setJoiningDate(row.getCell(25) != null ? row.getCell(25).getStringCellValue() : null);
                professionalDetail
                        .setAdditionalInfo(row.getCell(26) != null ? row.getCell(26).getStringCellValue() : null);
                        

                user = this.userServiceImpl.registerUser(user);
                if(row.getCell(24)!=null){
                    double currentSalary = (double) row.getCell(24).getNumericCellValue();
                    salary.setGrossSalary(currentSalary);
                    salary.setUser(user);
                    this.salaryServiceImpl.addSalary(salary);
                }
        
                bankDetails.setUser(user);
                this.bankDetailsServiceImpl.addBankDetails(bankDetails);

                professionalDetail.setUser(user);
                this.professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);

            } catch (Exception e) {
                errorEmails.add(row.getCell(4).getStringCellValue());
                continue;
            }
        }

        workbook.close();
        return errorEmails;

    }

    public byte[] emptyEmployeeSheet() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // // Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Employee Id", "First Name", "Last Name", "Gender", "Email", "Official Email", "Mobile No",
                "Emergency Mobile No",
                "Adhar No", "dob", "Present Address", "Permanent Address", "Bank Name", "Account No", "IFSC Code",
                "Pan No", "UAN No",
                "Total Experience", "Location", "Source of hire", "Position", "Department",
                "Skills", "Highest Qualification", "Current Salary", "Joining Date", "Adition info" };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("ABC123");
        row.createCell(0).setCellValue("John");
        row.createCell(1).setCellValue("Doe");
        row.createCell(2).setCellValue("Male");
        row.createCell(3).setCellValue("john.doe@example.com");
        row.createCell(4).setCellValue("john.doe@company.com");
        row.createCell(5).setCellValue(1234567890);
        row.createCell(6).setCellValue(987654321);
        row.createCell(7).setCellValue(12345677792l);
        row.createCell(8).setCellValue("1990-01-15");
        row.createCell(9).setCellValue("123 Main St, New York");
        row.createCell(10).setCellValue("456 Elm St, Los Angeles");
        row.createCell(11).setCellValue("ABC Bank");
        row.createCell(12).setCellValue(9876543210l);
        row.createCell(13).setCellValue("IFSC0001234");
        row.createCell(14).setCellValue("ABCDE1234F");
        row.createCell(15).setCellValue(123456789012l);
        row.createCell(16).setCellValue("5 Years");
        row.createCell(17).setCellValue("New York");
        row.createCell(18).setCellValue("LinkedIn");
        row.createCell(19).setCellValue("Software Engineer");
        row.createCell(20).setCellValue("IT Department");
        row.createCell(21).setCellValue("Java, Spring Boot, React");
        row.createCell(22).setCellValue("Master's in Computer Science");
        row.createCell(23).setCellValue(80000);
        row.createCell(24).setCellValue("2023-06-15");
        row.createCell(25).setCellValue("Top performer in Java Development");

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public byte[] emptyCandidateSheet() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // // Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = { "First Name", "Last Name", "Gender", "Email", "Official Email", "Mobile No",
                "Emergency Mobile No",
                "Adhar No", "dob", "Present Address", "Permanent Address", "Bank Name", "Account No", "IFSC Code",
                "Pan No", "UAN No",
                "Total Experience", "Location", "Source of hire", "Position", "Department",
                "Skills", "Highest Qualification", "Current Salary", "Joining Date", "Adition info" };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        Row row = sheet.createRow(1);

        row.createCell(0).setCellValue("John");
        row.createCell(1).setCellValue("Doe");
        row.createCell(2).setCellValue("Male");
        row.createCell(3).setCellValue("john.doe@example.com");
        row.createCell(4).setCellValue("john.doe@company.com");
        row.createCell(5).setCellValue(1234567890);
        row.createCell(6).setCellValue(987654321);
        row.createCell(7).setCellValue(12345677792l);
        row.createCell(8).setCellValue("1990-01-15");
        row.createCell(9).setCellValue("123 Main St, New York");
        row.createCell(10).setCellValue("456 Elm St, Los Angeles");
        row.createCell(11).setCellValue("ABC Bank");
        row.createCell(12).setCellValue(9876543210l);
        row.createCell(13).setCellValue("IFSC0001234");
        row.createCell(14).setCellValue("ABCDE1234F");
        row.createCell(15).setCellValue(123456789012l);
        row.createCell(16).setCellValue("5 Years");
        row.createCell(17).setCellValue("New York");
        row.createCell(18).setCellValue("LinkedIn");
        row.createCell(19).setCellValue("Software Engineer");
        row.createCell(20).setCellValue("IT Department");
        row.createCell(21).setCellValue("Java, Spring Boot, React");
        row.createCell(22).setCellValue("Master's in Computer Science");
        row.createCell(23).setCellValue(80000);
        row.createCell(24).setCellValue("2023-06-15");
        row.createCell(25).setCellValue("Top performer in Java Development");

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}