package com.taskmaster.task.model;

import com.taskmaster.project.entity.Project;
import com.taskmaster.status.entity.Status;
import com.taskmaster.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    private Long taskId;

    @NotBlank(message = "Summary is mandatory")
    @Size(min = 3, max = 100, message = "Task Summary must be between 3 minimum characters")
    private String summary;

    private String description;

    private String dueDate;

    private User createdBy;

    private Status status;

    private Project project;

    private String createdAt;

    private String updatedAt;

    private User assignedTo;
}
