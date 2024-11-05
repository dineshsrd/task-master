package com.taskmaster.task.util;

import com.taskmaster.project.util.ProjectUtil;
import com.taskmaster.status.util.StatusUtil;
import com.taskmaster.task.entity.Task;
import com.taskmaster.task.model.TaskResponseDTO;
import com.taskmaster.user.util.UserUtil;
import com.taskmaster.comment.util.CommentUtil;

public class TaskUtil {
    public static TaskResponseDTO getTaskResponseDTO(Task task) {
        TaskResponseDTO taskResponse = new TaskResponseDTO();
        taskResponse.setId(task.getId());
        taskResponse.setKey(task.getKey());
        taskResponse.setSummary(task.getSummary());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setDue_date(task.getDueDate());
        taskResponse.setCreated_at(task.getCreatedAt());
        taskResponse.setUpdated_at(task.getUpdatedAt());
        taskResponse.setCreated_by(UserUtil.getUserDTO(task.getCreatedBy()));
        taskResponse.setAssigned_to(UserUtil.getUserDTO(task.getAssignedTo()));
        taskResponse.setStatus(StatusUtil.getStatusDto(task.getStatus()));
        taskResponse.setProject(ProjectUtil.getProjectDto(task.getProject()));
        taskResponse.setComments(CommentUtil.getAllComments(task.getComments()));
        return taskResponse;
    }
}
