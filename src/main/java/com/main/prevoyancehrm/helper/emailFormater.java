package com.main.prevoyancehrm.helper;

public class EmailFormater {

    public static String generateWelcomeEmail(String email, String name, String position, String mobileNo) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html>")
                    .append("<body>")
                    .append("<p>Dear <b>").append(name).append("</b>,</p>")
                    .append("<p>")
                    .append("Welcome to <b>Prevoyance IT Solutions Pvt. Ltd.</b>! We are thrilled to have you on our team. ")
                    .append("Below are your onboarding details:")
                    .append("</p>")
                    .append("<h3>Employee Details:</h3>")
                    .append("<ul>")
                    .append("<li><b>Name:</b> ").append(name).append("</li>")
                    .append("<li><b>Email:</b> ").append(email).append("</li>")
                    .append("<li><b>Mobile No:</b> ").append(mobileNo).append("</li>")
                    .append("<li><b>Position:</b> ").append(position).append("</li>")
                    .append("</ul>")
                    .append("<p>")
                    .append("You can now log in to the website to create your password by clicking the link below:")
                    .append("</p>")
                    .append("<p>")
                    .append("<a href=\"http://localhost:5173/createPassword?email=")
                    .append(email)
                    .append("\">Set Your Password</a>")
                    .append("</p>")
                    .append("<p>")
                    .append("We are confident that you will be an excellent addition to our team and look forward to seeing your contributions.")
                    .append("</p>")
                    .append("<p>")
                    .append("If you have any questions, please don’t hesitate to reach out.")
                    .append("</p>")
                    .append("<br>")
                    .append("<p>Warm Regards,</p>")
                    .append("<p><b>Prevoyance IT Solutions Pvt. Ltd. HR Team</b></p>")
                    .append("</body>")
                    .append("</html>");

        return emailContent.toString();
    }
}
