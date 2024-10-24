package com.taskmaster.task.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.project.repository.ProjectRepository;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.status.repository.StatusRepository;
import com.taskmaster.task.model.TaskModel;
import com.taskmaster.task.repository.TaskRepository;
import com.taskmaster.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

    private TaskRepository _taskRepository;
    private ProjectRepository _projectRepository;
    private UserRepository _userRepository;
    private StatusRepository _statusRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, StatusRepository statusRepository) {
        this._taskRepository = taskRepository;
        this._projectRepository = projectRepository;
        this._userRepository = userRepository;
        this._statusRepository = statusRepository;
    }

    public ResponseEntity<ResponseModel> createTask(TaskModel taskModel, HttpServletRequest request) {
        try {

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}

