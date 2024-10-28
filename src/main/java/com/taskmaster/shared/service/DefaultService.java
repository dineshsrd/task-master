package com.taskmaster.shared.service;

import java.sql.Timestamp;
import java.util.logging.Logger;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.entity.ProjectUserRole;
import com.taskmaster.role.entity.Role;
import com.taskmaster.status.entity.Status;
import com.taskmaster.task.entity.Task;
import com.taskmaster.user.entity.User;
import com.taskmaster.status.enums.StatusEnum;
import com.taskmaster.project.repository.ProjectRepository;
import com.taskmaster.project.repository.ProjectUserRoleRepository;
import com.taskmaster.role.repository.RoleRepository;
import com.taskmaster.status.repository.StatusRepository;
import com.taskmaster.task.repository.TaskRepository;
import com.taskmaster.user.repository.UserRepository;
import com.taskmaster.project.util.ProjectUserRoleId;
import com.taskmaster.role.enums.RoleEnum;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class DefaultService {
    private static final Logger LOGGER = Logger.getLogger(DefaultService.class.getName());

    private final RoleRepository _roleRepository;
    private final StatusRepository _statusRepository;
    private final ProjectRepository _projectRepository;
    private final UserRepository _userRepository;
    private final TaskRepository _taskRepository;
    private final ProjectUserRoleRepository _projectUserRoleRepository;
    private final PasswordEncoder _passwordEncoder;

    public DefaultService(RoleRepository roleRepository, StatusRepository statusRepository, ProjectRepository _projectRepository, UserRepository _userRepository, TaskRepository _taskRepository, ProjectUserRoleRepository _projectUserRoleRepository, PasswordEncoder _passwordEncoder) {
        this._roleRepository = roleRepository;
        this._statusRepository = statusRepository;
        this._projectRepository = _projectRepository;
        this._userRepository = _userRepository;
        this._taskRepository = _taskRepository;
        this._projectUserRoleRepository = _projectUserRoleRepository;
        this._passwordEncoder = _passwordEncoder;
    }

    @PostConstruct
    public void initDefaultData() {
        addRoles();
        addUsers();
        addProjects();
        addStatuses();
        addUsersToProject();
    }

    @Transactional
    public void addRoles() {
        if (_roleRepository.count() == 0) {
            _roleRepository.save(new Role(1L, RoleEnum.ROLE_USER, "User", false));
            _roleRepository.save(new Role(2L, RoleEnum.ROLE_ADMIN, "Admin", false));
            _roleRepository.save(new Role(3L, RoleEnum.ROLE_PROJECT_ADMIN, "Owner", true));
            _roleRepository.save(new Role(4L, RoleEnum.ROLE_PROJECT_MANAGER, "Manager", true));
            _roleRepository.save(new Role(5L, RoleEnum.ROLE_PROJECT_USER, "Developer", true));
            LOGGER.info("Roles added successfully");
        } else {
            LOGGER.info("Roles already exist");
        }
    }

    @Transactional
    public void addStatuses() {
        if (_statusRepository.count() == 0) {
            _statusRepository.save(new Status(1L, StatusEnum.OPEN, "Open", true, null));
            _statusRepository.save(new Status(2L, StatusEnum.IN_PROGRESS, "In Progress", true, null));
            _statusRepository.save(new Status(3L, StatusEnum.CLOSED, "Closed", true, null));
            LOGGER.info("Statuses added successfully");
        } else {
            LOGGER.info("Statuses already exist");
        }
    }

    @Transactional
    public void addUsers() {
        if (_userRepository.count() == 0) {
            LOGGER.info("Adding users");
            User newUser = new User();
            newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            newUser.setFirstName("John");
            newUser.setLastName("Doe");
            newUser.setEmail("johndoe@gmail.com");
            newUser.setPassword(_passwordEncoder.encode("Test@123"));
            newUser.setApplicationRole(_roleRepository.findById(2L).get());
            _userRepository.save(newUser);
            LOGGER.info("Users added successfully");
        }
    }

    @Transactional
    public void addProjects() {
        if (_projectRepository.count() == 0) {
            Project pro1 = new Project();
            pro1.setName("Project 1");
            pro1.setKey("PROJ1");
            pro1.setDescription("Project 1 description");
            pro1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            pro1.setIsActive(true);
            pro1.setCreatedBy(_userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found")));
            _projectRepository.save(pro1);

            pro1 = new Project();
            pro1.setName("Project 2");
            pro1.setKey("PROJ2");
            pro1.setDescription("Project 2 description");
            pro1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            pro1.setIsActive(true);
            pro1.setCreatedBy(_userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found")));
            _projectRepository.save(pro1);

            LOGGER.info("Projects added successfully");
        } else {
            LOGGER.info("Projects already exist");
        }
    }



    @Transactional
    public void addUsersToProject() {
        if (_projectRepository.count() > 0) {
            User user = _userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            Project project = _projectRepository.findById(1L).orElseThrow(() -> new RuntimeException("Project not found"));
            Role role = _roleRepository.findById(3L).orElseThrow(() -> new RuntimeException("Role not found"));

            ProjectUserRoleId projectUserRoleId = new ProjectUserRoleId(1L, 1L, 3L);

            ProjectUserRole projectUserRole = new ProjectUserRole(projectUserRoleId, project, user, role);
            _projectUserRoleRepository.save(projectUserRole);
            LOGGER.info("Users added to project successfully");
        } else {
            LOGGER.info("Project does not exist");
        }
    }

    @Transactional
    public void addTasks() {
        if (_taskRepository.count() == 0) {
            LOGGER.info("Adding tasks");
            Task task = new Task();
            task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            task.setSummary("Task 1");
            task.setDescription("Task 1 description");
            task.setDueDate(new Timestamp(System.currentTimeMillis()+8640000L));
            Project project = _projectRepository.findById(1L).orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
            Status status = _statusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Status not found"));
            task.setStatus(status);
            User user = _userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            task.setCreatedBy(user);
            _taskRepository.save(task);
            LOGGER.info("Tasks added successfully");
        } else {
            LOGGER.info("Tasks already exist");
        }
    }

}
