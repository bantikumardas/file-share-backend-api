package com.fileshare.file_share_backend.service;


import com.fileshare.file_share_backend.model.File;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.tomcat.util.http.FastHttpDateFormat.formatDate;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${frontend.url}")
    private String frontEndURL;

    @Value("${backend.url}")
    private String backendURL;

    @Value("${sender.email}")
    private String senderEmail;

    public boolean sendEmail(File file) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(file.getEmailId());
            helper.setFrom(senderEmail);
            helper.setSubject("📦 File Shared via SafeDrop");

            String htmlMsg = "<div style='background:#e6f2ff;padding:20px;border-radius:10px;font-family:Segoe UI,Arial,sans-serif;font-size:16px;line-height:1.7;color:#333;'>"
                    + "<h2 style='color:#007BFF;'>Hello from <span style='color:#004080;'>SafeDrop</span> 👋</h2>"
                    + "<p>"+file.getSenderName()+" has securely shared a file with you via <strong>SafeDrop</strong>.</p>"
                    + "<p><strong>Please download this file before:</strong> " + formateDateAndTime(file.getExpiryTime()) + "</p>"

                    + "<div style='background:#ffffff;border-radius:8px;padding:15px;margin-top:15px;border:1px solid #ccc;'>"
                    + "<h3 style='color:#333;border-bottom:1px solid #eee;padding-bottom:8px;'>📁 File Details</h3>"
                    + "<ul style='list-style:none;padding:0;'>"
                    + "<li><strong>File Name:</strong> " + file.getFileName() + "</li>"
                    + "<li><strong>File Size:</strong> " + formatFileSize(file.getFileSize()) + "</li>"
                    + "<li><strong>File Link:</strong> <a href='" + backendURL+"/"+file.getFileUrl() + "' style='color:#007BFF;'>Click to Open</a></li>"
                    + "</ul>"
                    + "</div>"

                    + "<p style='margin-top:20px;'>To download this file, please visit our website:</p>"
                    + "<p><a href="+frontEndURL+" style='color:#007BFF;'>"+ frontEndURL +"</a></p>"
                    + "<p><strong>Key:</strong> " + file.getUniqueKey() + "</p>"
                    + "<p><strong>Code:</strong> " + file.getCode() + "</p>"

                    + "<br/>"
                    + "<p style='font-size:14px;color:#555;'>Thank you for using <strong>SafeDrop</strong>! 🔐</p>"
                    + "<p style='font-size:12px;color:#aaa;margin-top:10px;'>This is an auto-generated email. Please do not reply.</p>"
                    + "</div>";


            helper.setText(htmlMsg, true); // Set to true for HTML

            mailSender.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
            return false;
        }
    }

    private String formatFileSize(Long fileSize) {
        if (fileSize == 0) return "0 Bytes";

        int k = 1024;
        String[] sizes = {"Bytes", "KB", "MB", "GB", "TB"};
        int i = (int) (Math.floor(Math.log(fileSize) / Math.log(k)));

        double size = fileSize / Math.pow(k, i);
        return String.format("%.2f %s", size, sizes[i]);
    }

    private  String formateDateAndTime(Long epoch){
        if (epoch < 1000000000000L) {
            epoch *= 1000;
        }

        Date date = new Date(epoch);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        return sdf.format(date);
    }
}
