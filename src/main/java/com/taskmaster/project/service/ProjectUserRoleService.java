package com.taskmaster.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.entity.ProjectUserRole;
import com.taskmaster.role.entity.Role;
import com.taskmaster.user.entity.User;
import com.taskmaster.project.repository.ProjectRepository;
import com.taskmaster.project.repository.ProjectUserRoleRepository;
import com.taskmaster.role.repository.RoleRepository;
import com.taskmaster.user.repository.UserRepository;
import com.taskmaster.project.util.ProjectUserRoleId;

@Service
public class ProjectUserRoleService {

    @Autowired
    private ProjectUserRoleRepository projectUserRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void assignUserToProject(Long userId, Long projectId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

        ProjectUserRoleId projectUserRoleId = new ProjectUserRoleId(projectId, userId, roleId);

        // Assign the user to the project with the given role
        ProjectUserRole projectUserRole = new ProjectUserRole(projectUserRoleId, project, user, role);
        projectUserRoleRepository.save(projectUserRole);
    }
}
