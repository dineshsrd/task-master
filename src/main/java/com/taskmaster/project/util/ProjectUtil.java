package com.taskmaster.project.util;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.model.ProjectDTO;
import com.taskmaster.user.util.UserUtil;

public class ProjectUtil {
    public static ProjectDTO getProjectDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setKey(project.getKey());
        projectDTO.setUrl(project.getUrl());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setIsActive(project.getIsActive());
        projectDTO.setCreatedAt(project.getCreatedAt());
        projectDTO.setUpdatedAt(project.getUpdatedAt());
        projectDTO.setCreatedBy(UserUtil.getUserDTO(project.getCreatedBy()));
        return projectDTO;
    }
}
