package com.taskmaster.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.role.entity.Role;
import com.taskmaster.role.enums.RoleEnum;
import com.taskmaster.role.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository _roleRepository;

    public Role getRoleByName(RoleEnum roleEnum) {
        return _roleRepository.findByName(roleEnum).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
