package com.boms.controller;

import com.boms.entity.ContactInquiry;
import com.boms.service.ContactInquiryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    
    private final ContactInquiryService inquiryService;
    
    public HomeController(ContactInquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("inquiry", new ContactInquiry());
        return "contact";
    }
    
    @PostMapping("/contact/submit")
    public String submitContact(@ModelAttribute ContactInquiry inquiry, RedirectAttributes redirectAttributes) {
        try {
            inquiryService.createInquiry(inquiry);
            redirectAttributes.addFlashAttribute("success", "Thank you! Your inquiry has been submitted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sorry, there was an error submitting your inquiry. Please try again.");
        }
        return "redirect:/contact";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}