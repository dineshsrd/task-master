package com.taskmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.entity.ProjectUserRole;
import com.taskmaster.util.project.ProjectUserRoleId;

public interface ProjectUserRoleRepository extends JpaRepository<ProjectUserRole, ProjectUserRoleId> {
    ProjectUserRole findByProjectIdAndUserIdAndRoleId(Long projectId, Long userId, Long roleId);
    void deleteByProjectIdAndUserIdAndRoleId(Long projectId, Long userId, Long roleId);
}
