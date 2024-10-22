package com.taskmaster.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.project.entity.Project;
import com.taskmaster.status.entity.Status;
import com.taskmaster.task.entity.Task;
import com.taskmaster.user.entity.User;
import com.taskmaster.project.repository.ProjectRepository;
import com.taskmaster.status.repository.StatusRepository;
import com.taskmaster.task.repository.TaskRepository;
import com.taskmaster.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Transactional
    public Task createTask(String title, String description, Long projectId, Long creatorId, Long assigneeId, Long statusId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User (creator) not found"));

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("User (assignee) not found"));

        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        Task task = new Task();
        task.setSummary(title);
        task.setDescription(description);
        task.setProject(project);
        task.setCreatedBy(creator);
        task.setAssignedTo(assignee);
        task.setStatus(status);
        return taskRepository.save(task);
    }
}

