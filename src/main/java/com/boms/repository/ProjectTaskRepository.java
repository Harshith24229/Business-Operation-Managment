package com.boms.repository;

import com.boms.entity.Project;
import com.boms.entity.ProjectTask;
import com.boms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    List<ProjectTask> findByProject(Project project);
    List<ProjectTask> findByAssignedTo(User user);
    List<ProjectTask> findByStatus(ProjectTask.Status status);
}