package com.boms.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "boms_project_proposals")
public class ProjectProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "inquiry_id")
    private ContactInquiry inquiry;
    
    @Column(name = "project_title", nullable = false)
    private String projectTitle;
    
    @Column(nullable = false)
    private String client;
    
    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;
    
    private String duration;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status = Status.PENDING;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public ProjectProposal() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ContactInquiry getInquiry() { return inquiry; }
    public void setInquiry(ContactInquiry inquiry) { this.inquiry = inquiry; }
    
    public String getProjectTitle() { return projectTitle; }
    public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }
    
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    
    public BigDecimal getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; }
    
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}