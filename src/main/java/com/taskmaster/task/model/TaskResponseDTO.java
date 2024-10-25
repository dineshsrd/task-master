package com.taskmaster.task.model;

import java.sql.Timestamp;

import com.taskmaster.project.model.ProjectDTO;
import com.taskmaster.status.model.StatusDTO;
import com.taskmaster.user.model.UserDTO;

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
public class TaskResponseDTO {
    private Long id;
    private String key;
    private String summary;
    private String description;
    private StatusDTO status;
    private Timestamp due_date;
    private Timestamp created_at;
    private Timestamp updated_at;
    private UserDTO created_by;
    private UserDTO assigned_to;
    private ProjectDTO project;
}
