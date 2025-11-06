package com.boms.repository;

import com.boms.entity.ProjectProposal;
import com.boms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectProposalRepository extends JpaRepository<ProjectProposal, Long> {
    List<ProjectProposal> findByOrderByCreatedDateDesc();
    List<ProjectProposal> findByStatus(ProjectProposal.Status status);
    List<ProjectProposal> findByCreatedBy(User creator);
}