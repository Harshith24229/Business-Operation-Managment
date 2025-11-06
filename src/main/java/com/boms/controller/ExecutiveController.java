package com.boms.controller;

import com.boms.entity.ProjectProposal;
import com.boms.entity.User;
import com.boms.service.ProposalService;
import com.boms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/executive")
public class ExecutiveController {
    
    private final ProposalService proposalService;
    private final UserService userService;
    
    public ExecutiveController(ProposalService proposalService, UserService userService) {
        this.proposalService = proposalService;
        this.userService = userService;
    }
    
    @GetMapping("/proposals")
    public String proposals(Model model) {
        model.addAttribute("proposals", proposalService.getProposalsByStatus(ProjectProposal.Status.PENDING));
        return "executive/proposals";
    }
    
    @PostMapping("/proposals/{id}/approve")
    public String approveProposal(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        ProjectProposal proposal = proposalService.approveProposal(id, currentUser);
        if (proposal != null) {
            redirectAttributes.addFlashAttribute("success", "Proposal approved successfully. Client has been notified.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to approve proposal.");
        }
        return "redirect:/executive/proposals";
    }
    
    @PostMapping("/proposals/{id}/reject")
    public String rejectProposal(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ProjectProposal proposal = proposalService.rejectProposal(id);
        if (proposal != null) {
            redirectAttributes.addFlashAttribute("success", "Proposal rejected successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to reject proposal.");
        }
        return "redirect:/executive/proposals";
    }
}