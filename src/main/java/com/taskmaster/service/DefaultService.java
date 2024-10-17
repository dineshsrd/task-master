package com.taskmaster.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.taskmaster.entity.Project;
import com.taskmaster.entity.Role;
import com.taskmaster.entity.Status;
import com.taskmaster.repository.ProjectRepository;
import com.taskmaster.repository.RoleRepository;
import com.taskmaster.repository.StatusRepository;
import com.taskmaster.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DefaultService {
    private static final Logger LOGGER = Logger.getLogger(DefaultService.class.getName());

    private final RoleRepository _roleRepository;
    private final StatusRepository _statusRepository;
    private final ProjectRepository _projectRepository;
    private final UserRepository _userRepository;

    public DefaultService(RoleRepository roleRepository, StatusRepository statusRepository, ProjectRepository _projectRepository, UserRepository _userRepository) {
        this._roleRepository = roleRepository;
        this._statusRepository = statusRepository;
        this._projectRepository = _projectRepository;
        this._userRepository = _userRepository;
    }

    @PostConstruct
    public void addRoles() {
        if (_roleRepository.count() == 0) {
            _roleRepository.save(new Role(1L, "ROLE_USER", "User"));
            _roleRepository.save(new Role(2L, "ROLE_ADMIN", "Admin"));
            LOGGER.info("Roles added successfully");
        } else {
            LOGGER.info("Roles already exist");
        }
    }

    @PostConstruct
    public void addStatuses() {
        if (_statusRepository.count() == 0) {
            _statusRepository.save(new Status(1L, "open", "Open", true));
            _statusRepository.save(new Status(2L, "in_progress", "In Progress", true));
            _statusRepository.save(new Status(3L, "closed", "Closed", true));
            LOGGER.info("Statuses added successfully");
        } else {
            LOGGER.info("Statuses already exist");
        }
    }

    @PostConstruct
    public void addProjects() {
        if (_projectRepository.count() == 0) {
            Project pro1 = new Project();
            pro1.setName("Project 1");
            pro1.setKey("PROJ1");
            pro1.setDescription("Project 1 description");
            pro1.setCreatedAt(System.currentTimeMillis());
            pro1.setIsActive(true);
            _projectRepository.save(pro1);

            pro1 = new Project();
            pro1.setName("Project 2");
            pro1.setKey("PROJ2");
            pro1.setDescription("Project 2 description");
            pro1.setCreatedAt(System.currentTimeMillis());
            pro1.setIsActive(true);
            _projectRepository.save(pro1);

            LOGGER.info("Projects added successfully");
        } else {
            LOGGER.info("Projects already exist");
        }
    }

}
