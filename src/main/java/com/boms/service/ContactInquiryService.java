package com.boms.service;

import com.boms.entity.ContactInquiry;
import com.boms.entity.User;
import com.boms.repository.ContactInquiryRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContactInquiryService {
    
    private final ContactInquiryRepository inquiryRepository;
    private final JavaMailSender mailSender;
    private final SystemLogService logService;
    
    public ContactInquiryService(ContactInquiryRepository inquiryRepository, JavaMailSender mailSender, SystemLogService logService) {
        this.inquiryRepository = inquiryRepository;
        this.mailSender = mailSender;
        this.logService = logService;
    }
    
    public ContactInquiry createInquiry(ContactInquiry inquiry) {
        ContactInquiry saved = inquiryRepository.save(inquiry);
        sendNotificationEmail(saved);
        logService.log("INQUIRY_SUBMITTED", "New inquiry from: " + inquiry.getName());
        return saved;
    }
    
    public List<ContactInquiry> getAllInquiries() {
        return inquiryRepository.findByOrderByCreatedDateDesc();
    }
    
    public List<ContactInquiry> getInquiriesByStatus(ContactInquiry.Status status) {
        return inquiryRepository.findByStatus(status);
    }
    
    public ContactInquiry assignInquiry(Long inquiryId, User assignedTo) {
        Optional<ContactInquiry> inquiry = inquiryRepository.findById(inquiryId);
        if (inquiry.isPresent()) {
            ContactInquiry inq = inquiry.get();
            inq.setAssignedTo(assignedTo);
            return inquiryRepository.save(inq);
        }
        return null;
    }
    
    public ContactInquiry updateStatus(Long inquiryId, ContactInquiry.Status status) {
        Optional<ContactInquiry> inquiry = inquiryRepository.findById(inquiryId);
        if (inquiry.isPresent()) {
            ContactInquiry inq = inquiry.get();
            inq.setStatus(status);
            return inquiryRepository.save(inq);
        }
        return null;
    }
    
    public Optional<ContactInquiry> findById(Long id) {
        return inquiryRepository.findById(id);
    }
    
    private void sendNotificationEmail(ContactInquiry inquiry) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("neelureddy369@gmail.com");
            message.setTo("neelureddy369@gmail.com");
            message.setSubject("BOMS - New Contact Inquiry from " + inquiry.getName());
            message.setText("Dear Admin,\n\n" +
                          "A new contact inquiry has been submitted:\n\n" +
                          "Name: " + inquiry.getName() + "\n" +
                          "Email: " + inquiry.getEmail() + "\n" +
                          "Phone: " + (inquiry.getPhone() != null ? inquiry.getPhone() : "Not provided") + "\n" +
                          "Message: " + inquiry.getMessage() + "\n\n" +
                          "Please log in to BOMS to manage this inquiry.\n\n" +
                          "Best regards,\nBOMS System");
            mailSender.send(message);
            System.out.println("✅ Notification email sent to admin for inquiry: " + inquiry.getName());
        } catch (Exception e) {
            System.err.println("❌ Failed to send notification email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}