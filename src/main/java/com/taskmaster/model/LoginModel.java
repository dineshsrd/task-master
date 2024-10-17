package com.taskmaster.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginModel {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "LoginModel{" +
                ", email='" + email + '\'' +
                '}';
    }
}
