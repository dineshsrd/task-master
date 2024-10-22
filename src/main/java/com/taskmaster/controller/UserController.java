package com.taskmaster.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.model.ResponseModel;
import com.taskmaster.model.UserModel;
import com.taskmaster.service.RoleService;
import com.taskmaster.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService _userService;
    private final RoleService _roleService;

    private UserController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this._userService = userService;
        this._roleService = roleService;
    }

    @GetMapping(path = "/fetch/{userId}")
    public ResponseEntity<ResponseModel> fetch(@PathVariable Long userId, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            return _userService.fetchUser(userId, request);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/update/{userId}")
    public ResponseEntity<ResponseModel> update(@PathVariable Long userId, @RequestBody UserModel user) {
        ResponseModel response = new ResponseModel();
        try {
            return _userService.updateUser(userId, user);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<ResponseModel> delete(@PathVariable Long userId) {
        ResponseModel response = new ResponseModel();
        try {
            return _userService.deleteUser(userId);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public String currentUserName(Principal principal) {
        return principal.getName();
    }


}
