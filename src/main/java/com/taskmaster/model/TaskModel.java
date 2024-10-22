package com.taskmaster.model;

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
    private String title;
    private String description;
    private String dueDate;
    private Long userId;
    private Long statusId;
    private Long projectId;
    private String createdAt;
    private String updatedAt;

}
