//@formatter:off

package com.taskmaster.user.service;

import java.security.Principal;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmaster.shared.constant.ExceptionConstant;import com.taskmaster.shared.constant.UserMessageConstant;
import com.taskmaster.user.entity.User;
import com.taskmaster.role.enums.RoleEnum;
import com.taskmaster.exception.UserLevelException;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.user.model.UserModel;
import com.taskmaster.role.repository.RoleRepository;
import com.taskmaster.user.repository.UserRepository;
import com.taskmaster.role.util.RoleUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private UserRepository _userRepository;
    private PasswordEncoder _passwordEncoder;
    private RoleRepository _roleRepository;

    public UserService(@Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder, @Autowired RoleRepository roleRepository) {
        this._userRepository = userRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleRepository = roleRepository;
    }

    public UserDetails findByEmail(String email) {
        return _userRepository.findByEmail(email).orElseThrow();
    }

    public ResponseModel register(UserModel user) {
        User taskMasterUser;
        String message;
        HttpStatus status;
        try {
            if (_userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new UserLevelException(UserMessageConstant.USER_EXISTS);
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
            message = UserMessageConstant.USER_REGISTERED;
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            taskMasterUser = null;
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
            LOGGER.severe(ExceptionConstant.EXCEPTION_CREATE_USER);
        }
        return new ResponseModel(message, status, taskMasterUser);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ResponseModel> fetchUser(Long userId, HttpServletRequest request){
        ResponseModel response = new ResponseModel();
        User fetchedUser = (User) findById(userId);
        Principal principal = request.getUserPrincipal();
        System.out.println(principal.toString());
        UserModel userResponse = new UserModel();
        if (fetchedUser == null) {
            response.setMessage("User not found");
            response.setStatus(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            userResponse.setUser_id(fetchedUser.getId());
            userResponse.setFirst_name(fetchedUser.getFirstName());
            userResponse.setLast_name(fetchedUser.getLastName());
            userResponse.setEmail(fetchedUser.getEmail());
            userResponse.setCompany(fetchedUser.getCompany());
            userResponse.setDesignation(fetchedUser.getDesignation());
            response.setMessage(UserMessageConstant.USER_FETCHED);
            response.setStatus(HttpStatus.OK);
            response.setData(userResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public UserDetails findById(Long userId) {
        return _userRepository.findById(userId).orElse(null);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ResponseModel> updateUser(Long userId, UserModel user) {
        ResponseModel response = new ResponseModel();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = new RoleUtil().hasRole(RoleEnum.ROLE_ADMIN.toString());
            User fetchedUser = (User) findById(userId);
            String currentUsername = authentication.getName();
            if (fetchedUser == null) {
                response.setMessage(ExceptionConstant.USER_NOT_FOUND);
                response.setStatus(HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else if (fetchedUser.getEmail().equals(currentUsername) || isAdmin) {
                fetchedUser.setFirstName(user.getFirst_name());
                fetchedUser.setLastName(user.getLast_name());
                fetchedUser.setCompany(user.getCompany());
                fetchedUser.setDesignation(user.getDesignation());
                User updatedUser = _userRepository.save(fetchedUser);
                response.setMessage(UserMessageConstant.USER_UPDATED);
                response.setStatus(HttpStatus.OK);
                response.setData(updatedUser);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage(new StringBuilder(ExceptionConstant.UNAUTHORIZED_ACCESS + " " + ExceptionConstant.USER_UPDATE_ERROR).toString());
                response.setStatus(HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Secured("ROLE_ADMIN")
    public ResponseEntity<ResponseModel> deleteUser(Long userId) {
        ResponseModel response = new ResponseModel();
        try {
            _userRepository.deleteById(userId);
            response.setMessage(UserMessageConstant.USER_DELETED);
            response.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage() + " " + UserMessageConstant.ADMIN_ONLY_ACTION);
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
