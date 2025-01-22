package com.main.prevoyancehrm.helper;


public class EmailFormater{
    
    public static String generateWelcomeEmail(String email,String name,String position,String mobileNo) {
        StringBuilder emailContent = new StringBuilder();
    
        emailContent.append("Dear ").append(name).append(",<br><br>");
        emailContent.append("Welcome to <b>Your Company</b>! We are thrilled to have you on our team. Below are your onboarding details:<br><br>");
        
        emailContent.append("<b>User Details:</b><br>");
        emailContent.append("- <b>Name:</b> ").append(name).append("<br>");
        emailContent.append("- <b>Email:</b> ").append(email).append("<br>");
        emailContent.append("- <b>Mobile No:</b> ").append(mobileNo).append("<br>");
        emailContent.append("- <b>Position:</b> ").append(position).append("<br><br>");
        
        emailContent.append("We are confident that you will be an excellent addition to our team and look forward to seeing your contributions.<br><br>");
        emailContent.append("If you have any questions, please don’t hesitate to reach out.<br><br>");
        emailContent.append("Warm Regards,<br>Your Company HR Team<br>");
        
        return emailContent.toString();
    }
    
}
