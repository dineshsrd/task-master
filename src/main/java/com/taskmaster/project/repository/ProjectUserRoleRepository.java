package com.taskmaster.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.project.entity.ProjectUserRole;
import com.taskmaster.project.util.ProjectUserRoleId;

public interface ProjectUserRoleRepository extends JpaRepository<ProjectUserRole, ProjectUserRoleId> {
    ProjectUserRole findByProjectIdAndUserIdAndRoleId(Long projectId, Long userId, Long roleId);
    void deleteByProjectIdAndUserIdAndRoleId(Long projectId, Long userId, Long roleId);

    boolean existsByProjectIdAndUserId(Long id, Long userId);
}
