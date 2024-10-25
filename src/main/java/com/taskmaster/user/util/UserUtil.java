package com.taskmaster.user.util;

import com.taskmaster.user.entity.User;
import com.taskmaster.user.model.UserDTO;

public class UserUtil {
    public static UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getId());
        userDTO.setFirst_name(user.getFirstName());
        userDTO.setLast_name(user.getLastName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
