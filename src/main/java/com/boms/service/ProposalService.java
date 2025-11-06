package com.boms.service;

import com.boms.entity.ContactInquiry;
import com.boms.entity.ProjectProposal;
import com.boms.entity.User;
import com.boms.repository.ProjectProposalRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {
    
    private final ProjectProposalRepository proposalRepository;
    private final JavaMailSender mailSender;
    private final SystemLogService logService;
    private final ProjectService projectService;
    
    public ProposalService(ProjectProposalRepository proposalRepository, JavaMailSender mailSender, 
                          SystemLogService logService, ProjectService projectService) {
        this.proposalRepository = proposalRepository;
        this.mailSender = mailSender;
        this.logService = logService;
        this.projectService = projectService;
    }
    
    public ProjectProposal createProposal(ProjectProposal proposal) {
        return proposalRepository.save(proposal);
    }
    
    public List<ProjectProposal> getAllProposals() {
        return proposalRepository.findByOrderByCreatedDateDesc();
    }
    
    public List<ProjectProposal> getProposalsByStatus(ProjectProposal.Status status) {
        return proposalRepository.findByStatus(status);
    }
    
    public Optional<ProjectProposal> findById(Long id) {
        return proposalRepository.findById(id);
    }
    
    public ProjectProposal approveProposal(Long proposalId, User user) {
        Optional<ProjectProposal> proposalOpt = proposalRepository.findById(proposalId);
        if (proposalOpt.isPresent()) {
            ProjectProposal proposal = proposalOpt.get();
            proposal.setStatus(ProjectProposal.Status.APPROVED);
            ProjectProposal saved = proposalRepository.save(proposal);
            
            // Automatically create project from approved proposal
            projectService.createProjectFromProposal(saved, user);
            
            sendApprovalEmail(saved);
            logService.log(user, "PROPOSAL_APPROVED", "Approved proposal: " + proposal.getProjectTitle());
            return saved;
        }
        return null;
    }
    
    public ProjectProposal rejectProposal(Long proposalId) {
        Optional<ProjectProposal> proposalOpt = proposalRepository.findById(proposalId);
        if (proposalOpt.isPresent()) {
            ProjectProposal proposal = proposalOpt.get();
            proposal.setStatus(ProjectProposal.Status.REJECTED);
            return proposalRepository.save(proposal);
        }
        return null;
    }
    
    private void sendApprovalEmail(ProjectProposal proposal) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("neelureddy369@gmail.com");
            message.setTo(proposal.getInquiry().getEmail());
            message.setSubject("BOMS - Project Proposal Approved: " + proposal.getProjectTitle());
            
            String costText = proposal.getEstimatedCost() != null ? "$" + proposal.getEstimatedCost() : "To be discussed";
            String durationText = proposal.getDuration() != null ? proposal.getDuration() : "To be discussed";
            
            message.setText("Dear " + proposal.getClient() + ",\n\n" +
                          "Congratulations! Your project proposal has been approved.\n\n" +
                          "Project Details:\n" +
                          "Title: " + proposal.getProjectTitle() + "\n" +
                          "Estimated Cost: " + costText + "\n" +
                          "Duration: " + durationText + "\n\n" +
                          "Our team will contact you within 24-48 hours to discuss the next steps " +
                          "and finalize the project details.\n\n" +
                          "Thank you for choosing our services!\n\n" +
                          "Best regards,\n" +
                          "BOMS Team\n" +
                          "Email: neelureddy369@gmail.com");
            
            mailSender.send(message);
            System.out.println("✅ Approval email sent to client: " + proposal.getInquiry().getEmail());
        } catch (Exception e) {
            System.err.println("❌ Failed to send approval email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}