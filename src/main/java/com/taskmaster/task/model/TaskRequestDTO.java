package com.taskmaster.task.model;

import com.taskmaster.project.constant.ProjectConstant;
import com.taskmaster.task.constant.TaskConstant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TaskRequestDTO {

    @NotBlank(message = TaskConstant.TASK_SUMMARY_MANDATORY)
    @Size(min = 3, max = 100, message = TaskConstant.TASK_SUMMARY_MIN_LENGTH)
    private String summary;

    private String description;

    private String due_date;

    @NotNull(message = ProjectConstant.PROJECT_ID_MANDATORY)
    private Long project_id;

}
