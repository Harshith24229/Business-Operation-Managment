package com.boms.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Order(3)
public class EmailTestService implements CommandLineRunner {

    private final JavaMailSender mailSender;

    public EmailTestService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void run(String... args) throws Exception {
        testEmailConfiguration();
    }

    private void testEmailConfiguration() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("neelureddy369@gmail.com");
            message.setTo("neelureddy369@gmail.com");
            message.setSubject("BOMS - Email Configuration Test");
            message.setText("This is a test email to verify BOMS email configuration is working correctly.\n\n" +
                          "If you receive this email, the email notifications are properly configured.\n\n" +
                          "BOMS System");
            
            mailSender.send(message);
            System.out.println("✅ Email configuration test successful - Test email sent to neelureddy369@gmail.com");
        } catch (Exception e) {
            System.err.println("❌ Email configuration test failed: " + e.getMessage());
            System.err.println("Please check your Gmail app password and internet connection");
        }
    }
}