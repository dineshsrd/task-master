package com.taskmaster.project.util;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.model.ProjectDTO;

public class ProjectUtil {
    public static ProjectDTO getProjectDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setKey(project.getKey());
        return projectDTO;
    }
}
