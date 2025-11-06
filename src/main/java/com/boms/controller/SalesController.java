package com.boms.controller;

import com.boms.entity.ContactInquiry;
import com.boms.entity.ProjectProposal;
import com.boms.entity.User;
import com.boms.service.ContactInquiryService;
import com.boms.service.ProposalService;
import com.boms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SalesController {
    
    private final ContactInquiryService inquiryService;
    private final ProposalService proposalService;
    private final UserService userService;
    
    public SalesController(ContactInquiryService inquiryService, ProposalService proposalService, UserService userService) {
        this.inquiryService = inquiryService;
        this.proposalService = proposalService;
        this.userService = userService;
    }
    
    @GetMapping("/inquiries")
    public String inquiries(Model model) {
        model.addAttribute("inquiries", inquiryService.getAllInquiries());
        model.addAttribute("salesTeam", userService.getUsersByRole(User.Role.SALES_TEAM));
        return "sales/inquiries";
    }
    
    @PostMapping("/inquiries/{id}/assign")
    public String assignInquiry(@PathVariable Long id, @RequestParam Long assignedToId, RedirectAttributes redirectAttributes) {
        User assignedTo = userService.findById(assignedToId).orElse(null);
        if (assignedTo != null) {
            inquiryService.assignInquiry(id, assignedTo);
            redirectAttributes.addFlashAttribute("success", "Inquiry assigned successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/sales/inquiries";
    }
    
    @PostMapping("/inquiries/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam ContactInquiry.Status status, RedirectAttributes redirectAttributes) {
        inquiryService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Status updated successfully.");
        return "redirect:/sales/inquiries";
    }
    
    @GetMapping("/proposals")
    public String proposals(Model model) {
        model.addAttribute("proposals", proposalService.getAllProposals());
        return "sales/proposals";
    }
    
    @GetMapping("/proposals/create/{inquiryId}")
    public String createProposal(@PathVariable Long inquiryId, Model model, Authentication auth) {
        ContactInquiry inquiry = inquiryService.findById(inquiryId).orElse(null);
        if (inquiry != null && inquiry.getStatus() == ContactInquiry.Status.CONVERTED) {
            User currentUser = userService.findByUsername(auth.getName()).orElse(null);
            ProjectProposal proposal = new ProjectProposal();
            proposal.setInquiry(inquiry);
            proposal.setClient(inquiry.getName());
            proposal.setCreatedBy(currentUser);
            model.addAttribute("proposal", proposal);
            model.addAttribute("inquiry", inquiry);
            return "sales/proposal-form";
        }
        return "redirect:/sales/inquiries";
    }
    
    @PostMapping("/proposals/save")
    public String saveProposal(@ModelAttribute ProjectProposal proposal, @RequestParam Long inquiryId, Authentication auth, RedirectAttributes redirectAttributes) {
        ContactInquiry inquiry = inquiryService.findById(inquiryId).orElse(null);
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        if (inquiry != null && currentUser != null) {
            proposal.setInquiry(inquiry);
            proposal.setCreatedBy(currentUser);
            proposalService.createProposal(proposal);
            redirectAttributes.addFlashAttribute("success", "Proposal created successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to create proposal.");
        }
        return "redirect:/sales/proposals";
    }
}