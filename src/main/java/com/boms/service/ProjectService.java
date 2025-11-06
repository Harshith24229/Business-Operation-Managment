package com.boms.service;

import com.boms.entity.Project;
import com.boms.entity.ProjectProposal;
import com.boms.entity.ProjectTask;
import com.boms.entity.User;
import com.boms.repository.ProjectRepository;
import com.boms.repository.ProjectTaskRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository taskRepository;
    private final JavaMailSender mailSender;
    private final SystemLogService logService;
    
    public ProjectService(ProjectRepository projectRepository, ProjectTaskRepository taskRepository, 
                         JavaMailSender mailSender, SystemLogService logService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.mailSender = mailSender;
        this.logService = logService;
    }
    
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
    public Project createProjectFromProposal(ProjectProposal proposal, User projectManager) {
        Project project = new Project();
        project.setProposal(proposal);
        project.setName(proposal.getProjectTitle());
        project.setBudget(proposal.getEstimatedCost());
        project.setProjectManager(projectManager);
        project.setStartDate(LocalDate.now());
        project.setStatus(Project.Status.PENDING);
        Project saved = projectRepository.save(project);
        logService.log(projectManager, "PROJECT_CREATED", "Created project: " + project.getName());
        return saved;
    }
    
    public List<Project> getAllProjects() {
        return projectRepository.findByOrderByCreatedAtDesc();
    }
    
    public List<Project> getProjectsByManager(User manager) {
        return projectRepository.findByProjectManager(manager);
    }
    
    public List<Project> getProjectsByStatus(Project.Status status) {
        return projectRepository.findByStatus(status);
    }
    
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }
    
    public Project completeProject(Long projectId, User user) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            project.setStatus(Project.Status.COMPLETED);
            project.setEndDate(LocalDate.now());
            Project saved = projectRepository.save(project);
            
            // Send email to admin
            sendProjectCompletionEmail(project);
            logService.log(user, "PROJECT_COMPLETED", "Completed project: " + project.getName());
            return saved;
        }
        return null;
    }
    
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }
    
    public ProjectTask createTask(ProjectTask task) {
        ProjectTask saved = taskRepository.save(task);
        logService.log(task.getAssignedTo(), "TASK_CREATED", "Created task: " + task.getTitle());
        return saved;
    }
    
    public List<ProjectTask> getTasksByProject(Project project) {
        return taskRepository.findByProject(project);
    }
    
    public List<ProjectTask> getTasksByUser(User user) {
        return taskRepository.findByAssignedTo(user);
    }
    
    public ProjectTask updateTask(ProjectTask task) {
        ProjectTask saved = taskRepository.save(task);
        logService.log(task.getAssignedTo(), "TASK_UPDATED", "Updated task: " + task.getTitle());
        return saved;
    }
    
    public Optional<ProjectTask> findTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    private void sendProjectCompletionEmail(Project project) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("neelureddy369@gmail.com");
            message.setTo("neelureddy369@gmail.com");
            message.setSubject("BOMS - Project Completed: " + project.getName());
            message.setText("Dear Admin,\n\n" +
                          "A project has been completed:\n\n" +
                          "Project: " + project.getName() + "\n" +
                          "Manager: " + project.getProjectManager().getFullName() + "\n" +
                          "Completion Date: " + project.getEndDate() + "\n" +
                          "Budget: $" + project.getBudget() + "\n\n" +
                          "Please review the project details in the admin dashboard.\n\n" +
                          "Best regards,\nBOMS System");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send project completion email: " + e.getMessage());
        }
    }
}