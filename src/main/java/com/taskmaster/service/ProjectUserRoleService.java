package com.taskmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.entity.Project;
import com.taskmaster.entity.ProjectUserRole;
import com.taskmaster.entity.Role;
import com.taskmaster.entity.User;
import com.taskmaster.repository.ProjectRepository;
import com.taskmaster.repository.ProjectUserRoleRepository;
import com.taskmaster.repository.RoleRepository;
import com.taskmaster.repository.UserRepository;
import com.taskmaster.util.project.ProjectUserRoleId;

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
