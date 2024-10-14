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
public class UserModel {
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String designation;
    private String company;
    private List<String> roles;

    @Override
    public String toString() {
        return "UserModel{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", designation='" + designation + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
