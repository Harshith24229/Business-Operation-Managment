package com.boms.repository;

import com.boms.entity.Project;
import com.boms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOrderByCreatedAtDesc();
    List<Project> findByStatus(Project.Status status);
    List<Project> findByProjectManager(User manager);
}