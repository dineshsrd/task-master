package com.taskmaster.project.model;

import java.sql.Timestamp;

import com.taskmaster.user.entity.User;
import com.taskmaster.user.model.UserDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private Long id;
    @NotNull(message = "Project name is mandatory")
    private String name;
    @NotNull(message = "Project key is mandatory")
    private String key;
    private String url;
    private String description;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UserDTO createdBy;

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
