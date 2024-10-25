package com.taskmaster.task.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.repository.ProjectRepository;
import com.taskmaster.project.repository.ProjectUserRoleRepository;
import com.taskmaster.project.util.ProjectUtil;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.status.entity.Status;
import com.taskmaster.status.enums.StatusEnum;
import com.taskmaster.status.model.StatusDTO;
import com.taskmaster.status.repository.StatusRepository;
import com.taskmaster.task.entity.Task;
import com.taskmaster.task.model.TaskRequestDTO;
import com.taskmaster.task.model.TaskResponseDTO;
import com.taskmaster.task.repository.TaskRepository;
import com.taskmaster.task.util.TaskUtil;
import com.taskmaster.user.entity.User;
import com.taskmaster.user.model.UserDTO;
import com.taskmaster.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import com.taskmaster.status.util.StatusUtil;
import com.taskmaster.user.util.UserUtil;

@Service
public class TaskService {

    private TaskRepository _taskRepository;
    private ProjectRepository _projectRepository;
    private UserRepository _userRepository;
    private StatusRepository _statusRepository;
    private ProjectUserRoleRepository _projectUserRoleRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, StatusRepository statusRepository, ProjectUserRoleRepository _projectUserRoleRepository) {
        this._taskRepository = taskRepository;
        this._projectRepository = projectRepository;
        this._userRepository = userRepository;
        this._statusRepository = statusRepository;
        this._projectUserRoleRepository = _projectUserRoleRepository;
    }

    public ResponseEntity<ResponseModel> createTask(TaskRequestDTO taskModel, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            Project project = _projectRepository.findById(taskModel.getProject_id()).orElseThrow(() -> new RuntimeException("Project not found"));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Optional<User> userData = _userRepository.findByEmail(userName);
            if (!_projectUserRoleRepository.existsByProjectIdAndUserId(project.getId(), userData.get().getId())) {
                throw new RuntimeException("You are not allowed to create task in this project");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(taskModel.getDue_date());
            long millis = date.getTime();

            Task task = new Task();
            task.setSummary(taskModel.getSummary());
            task.setDescription(taskModel.getDescription());
            task.setProject(project);
            task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            task.setStatus(_statusRepository.findByName(StatusEnum.OPEN).get());
            task.setCreatedBy(userData.get());
            task.setAssignedTo(userData.get());
            task.setDueDate(new Timestamp(millis));
            task.setKey();

            TaskResponseDTO taskDTO = TaskUtil.getTaskResponseDTO(_taskRepository.save(task));

            response.setMessage("Task created successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(taskDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<ResponseModel> fetchTask(Long taskId, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            Task task = _taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
            TaskResponseDTO taskResponse = TaskUtil.getTaskResponseDTO(task);
            response.setMessage("Task fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(taskResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<ResponseModel> assign(Long taskId, UserDTO userData) {
        ResponseModel response = new ResponseModel();
        try {
            Task task = _taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
            User user = _userRepository.findById(userData.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
            task.setAssignedTo(user);
            Task updatedTask = _taskRepository.save(task);
            TaskResponseDTO taskResponse = TaskUtil.getTaskResponseDTO(updatedTask);
            response.setMessage("Task assigned successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(taskResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<ResponseModel> status(Long taskId, StatusDTO status) {
        ResponseModel response = new ResponseModel();
        try {
            Task task = _taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
            Status newStatus = !status.getId().equals(null)?_statusRepository.findById(status.getId()).orElseThrow(() -> new RuntimeException("Status not found, please provide a valid status ID")):_statusRepository.findByName(status.getName()).orElseThrow(() -> new RuntimeException("Status not found, please provide valid status name"));
            task.setStatus(newStatus);
            Task updatedTask = _taskRepository.save(task);
            TaskResponseDTO taskResponse = TaskUtil.getTaskResponseDTO(updatedTask);
            response.setMessage("Task assigned successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(taskResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<ResponseModel> fetchUserTasks(Long userId) {
        ResponseModel response = new ResponseModel();
        try {
            List<Task> assignedTasks = _taskRepository.fetchTasksByUserId(userId).orElseThrow(() -> new RuntimeException("Task not found"));
            List<TaskResponseDTO> taskResponse = assignedTasks.stream().map(task -> TaskUtil.getTaskResponseDTO(task)).toList();
            response.setMessage("User tasks fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(taskResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

