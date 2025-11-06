package com.boms.controller;

import com.boms.entity.Project;
import com.boms.entity.ProjectTask;
import com.boms.entity.User;
import com.boms.service.ProjectService;
import com.boms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/execution")
public class ExecutionController {
    
    private final ProjectService projectService;
    private final UserService userService;
    
    public ExecutionController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }
    
    @GetMapping("/projects")
    public String projects(Model model, Authentication auth) {
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        if (currentUser != null && currentUser.getRole() == User.Role.EXECUTIVE_TEAM) {
            model.addAttribute("projects", projectService.getProjectsByManager(currentUser));
        } else {
            model.addAttribute("projects", projectService.getAllProjects());
        }
        return "execution/projects";
    }
    
    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id).orElse(null);
        if (project != null) {
            model.addAttribute("project", project);
            model.addAttribute("tasks", projectService.getTasksByProject(project));
            model.addAttribute("executionTeam", userService.getUsersByRole(User.Role.EXECUTIVE_TEAM));
            model.addAttribute("newTask", new ProjectTask());
            return "execution/project-details";
        }
        return "redirect:/execution/projects";
    }
    
    @PostMapping("/projects/{id}/tasks")
    public String createTask(@PathVariable Long id, @ModelAttribute ProjectTask task, RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(id).orElse(null);
        if (project != null) {
            task.setProject(project);
            projectService.createTask(task);
            redirectAttributes.addFlashAttribute("success", "Task created successfully.");
        }
        return "redirect:/execution/projects/" + id;
    }
    
    @PostMapping("/tasks/{id}/update")
    public String updateTask(@PathVariable Long id, @RequestParam ProjectTask.Status status, 
                           @RequestParam Integer progress, RedirectAttributes redirectAttributes) {
        ProjectTask task = projectService.findTaskById(id).orElse(null);
        if (task != null) {
            task.setStatus(status);
            task.setProgressPercentage(progress);
            projectService.updateTask(task);
            redirectAttributes.addFlashAttribute("success", "Task updated successfully.");
            return "redirect:/execution/projects/" + task.getProject().getId();
        }
        return "redirect:/execution/projects";
    }
    
    @PostMapping("/projects/{id}/complete")
    public String completeProject(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        Project project = projectService.completeProject(id, currentUser);
        if (project != null) {
            redirectAttributes.addFlashAttribute("success", "Project completed successfully. Admin has been notified.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to complete project.");
        }
        return "redirect:/execution/projects";
    }
    
    @GetMapping("/my-tasks")
    public String myTasks(Model model, Authentication auth) {
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        if (currentUser != null) {
            model.addAttribute("tasks", projectService.getTasksByUser(currentUser));
        }
        return "execution/my-tasks";
    }
}