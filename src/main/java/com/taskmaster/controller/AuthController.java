package com.taskmaster.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.entity.User;
import com.taskmaster.model.LoginModel;
import com.taskmaster.model.LoginResponseModel;
import com.taskmaster.model.ResponseModel;
import com.taskmaster.model.UserModel;
import com.taskmaster.service.AuthService;
import com.taskmaster.service.JwtService;
import com.taskmaster.service.ProjectUserRoleService;
import com.taskmaster.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService _userService;

    @Autowired
    private JwtService _jwtService;

    @Autowired
    private AuthService _authenticationService;

    @Autowired
    private ProjectUserRoleService _projectUserRoleService;

    public AuthController(JwtService jwtService, AuthService authenticationService) {
        this._jwtService = jwtService;
        this._authenticationService = authenticationService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseModel> register(@Valid @RequestBody UserModel user) {
        try {
            ResponseModel response = _userService.register(user);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            ResponseModel response = new ResponseModel(e.getMessage(), HttpStatus.BAD_REQUEST, null);
            return new ResponseEntity<>(response, response.getStatus());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseModel> authenticate(@RequestBody LoginModel loginUserDto) {
        ResponseModel response = new ResponseModel();
        System.out.println(loginUserDto.getEmail());
        System.out.println(loginUserDto.getPassword());
        try{
            UserDetails authenticatedUser = _authenticationService.authenticate(loginUserDto);
            User user = (User) authenticatedUser;
            LOGGER.info("User authenticated successfully. Username: " + user.getId());
            String jwtToken = _jwtService.generateToken(authenticatedUser);
            LoginResponseModel loginResponse = new LoginResponseModel();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(_jwtService.getExpirationTime());
            response.setMessage("Login successful!");
            response.setStatus(HttpStatus.OK);
            response.setData(loginResponse);
            _projectUserRoleService.assignUserToProject(user.getId(), 1L, 3L);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (Exception ex){
            LOGGER.severe(ex.getMessage());
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setData(null);
            return new ResponseEntity<>(response, response.getStatus());
        }

    }
}
