package com.taskmaster.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.status.model.StatusDTO;
import com.taskmaster.task.model.TaskRequestDTO;
import com.taskmaster.task.service.TaskService;
import com.taskmaster.user.model.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private TaskService _taskService;

    public TaskController(TaskService taskService) {
        this._taskService = taskService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> create(@Valid @RequestBody TaskRequestDTO taskModel, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            return _taskService.createTask(taskModel, request);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseModel> fetch(@PathVariable Long taskId, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            return _taskService.fetchTask(taskId, request);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{taskId}/assign")
    public ResponseEntity<ResponseModel> assign(@PathVariable Long taskId, @RequestBody UserDTO userData) {
        ResponseModel response = new ResponseModel();
        try {
            return _taskService.assign(taskId, userData);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<ResponseModel> assign(@PathVariable Long taskId, @RequestBody StatusDTO status) {
        ResponseModel response = new ResponseModel();
        try {
            return _taskService.status(taskId, status);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseModel> fetchTasksByUserId(@PathVariable Long userId) {
        ResponseModel response = new ResponseModel();
        try {
            return _taskService.fetchUserTasks(userId);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
