package com.taskmaster.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmaster.entity.User;
import com.taskmaster.exception.UserLevelException;
import com.taskmaster.model.ResponseModel;
import com.taskmaster.model.UserModel;
import com.taskmaster.repository.ProjectRepository;
import com.taskmaster.repository.RoleRepository;
import com.taskmaster.repository.UserRepository;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    @Autowired
    private RoleRepository _roleRepository;

    @Autowired
    private ProjectRepository _projectRepository;

    public ResponseModel register(UserModel user) {
        User taskMasterUser = null;
        String message;
        HttpStatus status;
        try {
            if (_userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new UserLevelException("User already exists");
            }
            taskMasterUser = new User();
            taskMasterUser.setFirstName(user.getFirst_name());
            taskMasterUser.setLastName(user.getLast_name());
            taskMasterUser.setEmail(user.getEmail());
            taskMasterUser.setPassword(_passwordEncoder.encode(user.getPassword()));
            taskMasterUser.setCompany(user.getCompany());
            taskMasterUser.setDesignation(user.getDesignation());
            taskMasterUser.setCreatedAt(System.currentTimeMillis());
            taskMasterUser.setUpdatedAt(null);
            taskMasterUser.setApplicationRole(_roleRepository.findById(user.getRole_id()).orElseThrow());
            _userRepository.save(taskMasterUser);
            message = "User registered successfully";
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            taskMasterUser = null;
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseModel(message, status, taskMasterUser);
    }

    public UserDetails findByEmail(String email) {
        return _userRepository.findByEmail(email).orElseThrow();
    }
}
