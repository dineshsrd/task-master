package com.taskmaster.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.entity.Role;
import com.taskmaster.enums.RoleEnum;
import com.taskmaster.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository _roleRepository;

    public Role getRoleByName(RoleEnum roleEnum) {
        return _roleRepository.findByName(roleEnum).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
