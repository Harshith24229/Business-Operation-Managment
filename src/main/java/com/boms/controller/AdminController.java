package com.boms.controller;

import com.boms.entity.User;
import com.boms.service.ContactInquiryService;
import com.boms.service.ProposalService;
import com.boms.service.ProjectService;
import com.boms.service.SystemLogService;
import com.boms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private final UserService userService;
    private final ContactInquiryService inquiryService;
    private final ProposalService proposalService;
    private final ProjectService projectService;
    private final SystemLogService logService;
    
    public AdminController(UserService userService, ContactInquiryService inquiryService, 
                          ProposalService proposalService, ProjectService projectService, SystemLogService logService) {
        this.userService = userService;
        this.inquiryService = inquiryService;
        this.proposalService = proposalService;
        this.projectService = projectService;
        this.logService = logService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Basic counts
        int totalInquiries = inquiryService.getAllInquiries().size();
        int totalProposals = proposalService.getAllProposals().size();
        int totalProjects = projectService.getAllProjects().size();
        
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalInquiries", totalInquiries);
        model.addAttribute("totalProposals", totalProposals);
        model.addAttribute("totalProjects", totalProjects);
        
        // Analytics data
        model.addAttribute("inquiries", inquiryService.getAllInquiries());
        model.addAttribute("proposals", proposalService.getAllProposals());
        model.addAttribute("projects", projectService.getAllProjects());
        
        // Conversion rates
        double inquiryToProposal = totalInquiries > 0 ? (double) totalProposals / totalInquiries * 100 : 0;
        double proposalToProject = totalProposals > 0 ? (double) totalProjects / totalProposals * 100 : 0;
        model.addAttribute("inquiryToProposalRate", String.format("%.1f", inquiryToProposal));
        model.addAttribute("proposalToProjectRate", String.format("%.1f", proposalToProject));
        
        return "admin/dashboard";
    }
    
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "admin/users";
    }
    
    @PostMapping("/users")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "User created successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    @PostMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id).orElse(null);
        if (user != null) {
            user.setEnabled(!user.isEnabled());
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "User status updated.");
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("/logs")
    public String systemLogs(Model model) {
        model.addAttribute("logs", logService.getAllLogs());
        return "admin/logs";
    }
    
    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalInquiries", inquiryService.getAllInquiries().size());
        model.addAttribute("totalProposals", proposalService.getAllProposals().size());
        model.addAttribute("totalProjects", projectService.getAllProjects().size());
        return "admin/analytics";
    }
}