package com.taskmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.model.LoginResponseModel;
import com.taskmaster.model.ResponseModel;
import com.taskmaster.model.UserModel;
import com.taskmaster.service.AuthService;
import com.taskmaster.service.JwtService;
import com.taskmaster.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService _userService;

    @Autowired
    private JwtService _jwtService;

    @Autowired
    private AuthService _authenticationService;

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
    public ResponseEntity<ResponseModel> authenticate(@RequestBody UserModel loginUserDto) {
        ResponseModel response = new ResponseModel();
        try{
            UserDetails authenticatedUser = _authenticationService.authenticate(loginUserDto);
            System.out.println(authenticatedUser);
            String jwtToken = _jwtService.generateToken(authenticatedUser);
            LoginResponseModel loginResponse = new LoginResponseModel();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(_jwtService.getExpirationTime());
            response.setMessage("Login successful!");
            response.setStatus(HttpStatus.OK);
            response.setData(loginResponse);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (Exception ex){
            ex.printStackTrace();
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setData(null);
            return new ResponseEntity<>(response, response.getStatus());
        }

    }
}
