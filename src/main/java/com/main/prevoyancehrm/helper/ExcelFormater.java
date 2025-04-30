package com.main.prevoyancehrm.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.EmailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.SalaryServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;
import com.main.prevoyancehrm.service.serviceLogic.EmployeeDefaultAssignElements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private EmployeeDefaultAssignElements assignElements;

    public byte[] exportEmployee(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

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

    public void importCandidatesFromExcel(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty. Please upload a valid Excel file.");
        }
    
        try {
            List<List<Object>> candidateDataList = parseCandidateXlsx(file);
            for (List<Object> data : candidateDataList) {
                User user = null;
                ProfessionalDetail professionalDetail = null;
                BankDetails bankDetails = null;
                Salary salary = null;
    
                for (Object obj : data) {
                    if (obj instanceof User) {
                        user = (User) obj;
                    } else if (obj instanceof ProfessionalDetail) {
                        professionalDetail = (ProfessionalDetail) obj;
                    } else if (obj instanceof BankDetails) {
                        bankDetails = (BankDetails) obj;
                    } else if (obj instanceof Salary) {
                        salary = (Salary) obj;
                    }
                }
    
                if (user != null && user.getEmail() != null && userServiceImpl.getUserByEmail(user.getEmail()) == null) {
                    user = userServiceImpl.registerUser(user);
    
                    if (salary != null) {
                        salary.setUser(user);
                        salaryServiceImpl.addSalary(salary);
                    }
    
                    if (bankDetails != null) {
                        bankDetails.setUser(user);
                        bankDetailsServiceImpl.addBankDetails(bankDetails);
                    }
    
                    if (professionalDetail != null) {
                        professionalDetail.setUser(user);
                        professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while importing candidate data: " + e.getMessage(), e);
        }
    }
    

    public List<String> importEmployee(MultipartFile file) throws IOException {
        List<String> errorEmails = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
    
            if (!rowIterator.hasNext()) return errorEmails;
            Row headerRow = rowIterator.next();
    
            // Map headers to column indices
            String[] headers = {
                "empId",
                "email", "firstName", "lastName", "gender", "officialEmail", "mobileNo",
                "emgMobileNo", "adharNo", "dob", "presentAddress", "permanentAddress",
                "bankName", "bankAccountNo", "ifscCode", "panNo", "uanNo", "totalExperience",
                "location", "hireSource", "position", "department", "skills", "highestQualification",
                "joiningDate", "additionalInfo", "grossSalary"
            };
            
            Map<String, Integer> headerIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                int idx = getColumnIndex(headerRow, headers[i]);
                if (idx != -1) {
                    headerIndex.put(headers[i], idx);
                }
            }
    
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    String email = getCellValue(row.getCell(headerIndex.get("email")));
                    if (userServiceImpl.getUserByEmail(email) != null) {
                        continue;
                    }
    
                    User user = new User();
                    user.setEmployeeId(getCellValue(row.getCell(headerIndex.get("empId"))));
                    user.setFirstName(getCellValue(row.getCell(headerIndex.get("firstName"))));
                    user.setLastName(getCellValue(row.getCell(headerIndex.get("lastName"))));
                    user.setGender(getCellValue(row.getCell(headerIndex.get("gender"))));
                    user.setEmail(email);
                    user.setOfficialEmail(getCellValue(row.getCell(headerIndex.get("officialEmail"))));
                    user.setMobileNo(getCellValue(row.getCell(headerIndex.get("mobileNo"))));
                    user.setEmgMobileNo(getCellValue(row.getCell(headerIndex.get("emgMobileNo"))));
                    user.setAdharNo(getCellValue(row.getCell(headerIndex.get("adharNo"))));
                    user.setDob(getCellValue(row.getCell(headerIndex.get("dob"))));
                    user.setPresentAddress(getCellValue(row.getCell(headerIndex.get("presentAddress"))));
                    user.setPermanentAddress(getCellValue(row.getCell(headerIndex.get("permanentAddress"))));
    
                    // Create and populate BankDetails entity
                    BankDetails bankDetails = new BankDetails();
                    bankDetails.setBankName(getCellValue(row.getCell(headerIndex.get("bankName"))));
                    bankDetails.setBankAccountNo(getCellValue(row.getCell(headerIndex.get("bankAccountNo"))));
                    bankDetails.setIfscCode(getCellValue(row.getCell(headerIndex.get("ifscCode"))));
                    bankDetails.setPanNo(getCellValue(row.getCell(headerIndex.get("panNo"))));
                    bankDetails.setUanNo(getCellValue(row.getCell(headerIndex.get("uanNo"))));
    
                    // Ensure ProfessionalDetail is initialized if not already set
                    if (user.getProfessionalDetail() == null) {
                        user.setProfessionalDetail(new ProfessionalDetail());
                    }
    
                    ProfessionalDetail professionalDetail = user.getProfessionalDetail();
                    professionalDetail.setTotalExperience(getCellValue(row.getCell(headerIndex.get("totalExperience"))));
                    professionalDetail.setLocation(getCellValue(row.getCell(headerIndex.get("location"))));
                    professionalDetail.setHireSource(getCellValue(row.getCell(headerIndex.get("hireSource"))));
                    professionalDetail.setPosition(getCellValue(row.getCell(headerIndex.get("position"))));
                    professionalDetail.setDepartment(getCellValue(row.getCell(headerIndex.get("department"))));
                    professionalDetail.setSkills(getCellValue(row.getCell(headerIndex.get("skills"))));
                    professionalDetail.setHighestQualification(getCellValue(row.getCell(headerIndex.get("highestQualification"))));
                    professionalDetail.setJoiningDate(getCellValue(row.getCell(headerIndex.get("joiningDate"))));
                    professionalDetail.setAdditionalInfo(getCellValue(row.getCell(headerIndex.get("additionalInfo"))));
    
                    user.setActive(true);
                    user.setDelete(false);
                    user.setApproved(true);
                    user.setRole(Role.EMPLOYEE);
                    User user2 = userServiceImpl.registerUser(user);
                    
                    String name = user.getFirstName() + " " + user.getLastName();
                    String position = user.getProfessionalDetail().getPosition();
                    String mobileNo = user.getMobileNo();
                    log.info("User registered successfully: " + user.getEmail());
    
                    CompletableFuture.runAsync(() -> this.assignElements.assignAllLeaveTypes(user2.getId()));
                    CompletableFuture.runAsync(() -> emailServiceImpl.welcomeEmail(email, name, position, mobileNo));
                    log.info("Verification mail sent successfully: " + user.getEmail());
    
                    // Optional salary
                    if (headerIndex.containsKey("grossSalary")) {
                        Cell salaryCell = row.getCell(headerIndex.get("grossSalary"));
                        if (salaryCell != null && salaryCell.getCellType() == CellType.NUMERIC) {
                            Salary salary = new Salary();
                            salary.setGrossSalary(salaryCell.getNumericCellValue());
                            salary.setUser(user2);
                            salaryServiceImpl.addSalary(salary);
                            log.info("Salary added successfully for user: " + user.getEmail());
                        }
                    }
    
                    // Save bank details
                    bankDetails.setUser(user2);
                    bankDetailsServiceImpl.addBankDetails(bankDetails);
    
                    // Save professional details
                    professionalDetail.setUser(user2);
                    professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);
    
                } catch (Exception e) {
                    e.printStackTrace();
                    String email = getCellValue(row.getCell(headerIndex.get("email")));
                    errorEmails.add(email);
                }
            }
        }
        return errorEmails;
    }
    
    
    

    public byte[] emptyEmployeeSheet() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "empId",
            "email", "firstName", "lastName", "gender", "officialEmail", "mobileNo",
            "emgMobileNo", "adharNo", "dob", "presentAddress", "permanentAddress",
            "bankName", "bankAccountNo", "ifscCode", "panNo", "uanNo", "totalExperience",
            "location", "hireSource", "position", "department", "skills", "highestQualification",
            "joiningDate", "additionalInfo", "grossSalary"
        };

        // Adding headers to the first row
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Adding a row with dummy data
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("EMP1");
        row.createCell(1).setCellValue("john.doe@example.com");
        row.createCell(2).setCellValue("John");
        row.createCell(3).setCellValue("Doe");
        row.createCell(4).setCellValue("Male");
        row.createCell(5).setCellValue("john.doe@company.com");
        row.createCell(6).setCellValue("1234567890");
        row.createCell(7).setCellValue("9876543210");
        row.createCell(8).setCellValue("123456789012");
        row.createCell(9).setCellValue("1990-01-15");
        row.createCell(10).setCellValue("123 Main St, New York");
        row.createCell(11).setCellValue("456 Elm St, Los Angeles");
        row.createCell(12).setCellValue("ABC Bank");
        row.createCell(13).setCellValue("1234567890123456");
        row.createCell(14).setCellValue("IFSC0001234");
        row.createCell(15).setCellValue("ABCDE1234F");
        row.createCell(16).setCellValue("123456789012");
        row.createCell(17).setCellValue("5 Years");
        row.createCell(18).setCellValue("New York");
        row.createCell(19).setCellValue("LinkedIn");
        row.createCell(20).setCellValue("Software Engineer");
        row.createCell(21).setCellValue("IT Department");
        row.createCell(22).setCellValue("Java, Spring Boot, React");
        row.createCell(23).setCellValue("Master's in Computer Science");
        row.createCell(24).setCellValue(75000);
        row.createCell(25).setCellValue("2023-06-15");
        row.createCell(26).setCellValue("Top performer in Java Development");

        // Auto-size columns for better visibility
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to byte array output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public byte[] emptyCandidateSheet() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");
    
        // Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "firstName", "lastName", "gender", "email", "officialEmail", "mobileNo", "emgMobileNo",
            "adharNo", "dob", "presentAddress", "permanentAddress",
            "bankName", "bankAccountNo", "ifscCode", "panNo", "uanNo",
            "totalExperience", "location", "hireSource", "position", "department", "skills", "highestQualification",
            "grossSalary", "joiningDate", "additionalInfo"
        };
    
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    
        // Dummy Data Row
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
    
        // Auto-size the columns to fit the headers
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
    
        return outputStream.toByteArray();
    }
    

    private List<List<Object>> parseCandidateXlsx(MultipartFile file) throws Exception {
    List<List<Object>> candidates = new ArrayList<>();

    try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        if (!rowIterator.hasNext()) return candidates;
        Row headerRow = rowIterator.next();

        // Map headers to their indexes
        Map<String, Integer> indexMap = new HashMap<>();
        String[] headers = {
            "firstName", "lastName", "gender", "email", "officialEmail", "mobileNo", "emgMobileNo",
            "adharNo", "dob", "presentAddress", "permanentAddress",
            "bankName", "bankAccountNo", "ifscCode", "panNo", "uanNo",
            "totalExperience", "location", "hireSource", "position", "department", "skills", "highestQualification",
            "grossSalary", "joiningDate", "additionalInfo"
        };

        for (String header : headers) {
            int idx = getColumnIndex(headerRow, header);
            if (idx == -1) throw new RuntimeException("Missing column: " + header);
            indexMap.put(header, idx);
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<Object> objects = new ArrayList<>();

            User user = new User();
            user.setFirstName(getCellValue(row.getCell(indexMap.get("firstName"))));
            user.setLastName(getCellValue(row.getCell(indexMap.get("lastName"))));
            user.setGender(getCellValue(row.getCell(indexMap.get("gender"))));
            user.setEmail(getCellValue(row.getCell(indexMap.get("email"))));
            user.setOfficialEmail(getCellValue(row.getCell(indexMap.get("officialEmail"))));
            user.setMobileNo(getCellValue(row.getCell(indexMap.get("mobileNo"))));
            user.setEmgMobileNo(getCellValue(row.getCell(indexMap.get("emgMobileNo"))));
            user.setAdharNo(getCellValue(row.getCell(indexMap.get("adharNo"))));
            user.setDob(getCellValue(row.getCell(indexMap.get("dob"))));
            user.setPresentAddress(getCellValue(row.getCell(indexMap.get("presentAddress"))));
            user.setPermanentAddress(getCellValue(row.getCell(indexMap.get("permanentAddress"))));

            BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName(getCellValue(row.getCell(indexMap.get("bankName"))));
            bankDetails.setBankAccountNo(getCellValue(row.getCell(indexMap.get("bankAccountNo"))));
            bankDetails.setIfscCode(getCellValue(row.getCell(indexMap.get("ifscCode"))));
            bankDetails.setPanNo(getCellValue(row.getCell(indexMap.get("panNo"))));
            bankDetails.setUanNo(getCellValue(row.getCell(indexMap.get("uanNo"))));

            ProfessionalDetail professionalDetail = new ProfessionalDetail();
            professionalDetail.setTotalExperience(getCellValue(row.getCell(indexMap.get("totalExperience"))));
            professionalDetail.setLocation(getCellValue(row.getCell(indexMap.get("location"))));
            professionalDetail.setHireSource(getCellValue(row.getCell(indexMap.get("hireSource"))));
            professionalDetail.setPosition(getCellValue(row.getCell(indexMap.get("position"))));
            professionalDetail.setDepartment(getCellValue(row.getCell(indexMap.get("department"))));
            professionalDetail.setSkills(getCellValue(row.getCell(indexMap.get("skills"))));
            professionalDetail.setHighestQualification(getCellValue(row.getCell(indexMap.get("highestQualification"))));
            professionalDetail.setJoiningDate(getCellValue(row.getCell(indexMap.get("joiningDate"))));
            professionalDetail.setAdditionalInfo(getCellValue(row.getCell(indexMap.get("additionalInfo"))));

            Salary salary = new Salary();
            salary.setGrossSalary(safeParseDouble(getCellValue(row.getCell(indexMap.get("grossSalary")))));

            objects.add(user);
            objects.add(professionalDetail);
            objects.add(bankDetails);
            objects.add(salary);

            candidates.add(objects);
        }
    }

    return candidates;
        }

        
        private int getColumnIndex(Row headerRow, String columnName) {
                for (Cell cell : headerRow) {
                    if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName.trim())) {
                        return cell.getColumnIndex();
                    }
                }
                return -1;
            }
        private String getCellValue(Cell cell) {
    if (cell == null) return "";
    switch (cell.getCellType()) {
        case STRING: return cell.getStringCellValue();
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate().toString();
            }
            return String.valueOf((long) cell.getNumericCellValue());
        case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
        default: return "";
    }
}

private Double safeParseDouble(String value) {
    try {
        return Double.parseDouble(value);
    } catch (Exception e) {
        return 0.0;
    }
}

            

}