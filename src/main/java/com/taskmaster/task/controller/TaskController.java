package com.taskmaster.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.shared.constant.GeneralConstants;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.task.model.TaskModel;
import com.taskmaster.task.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private TaskService _taskService;

    public TaskController(TaskService taskService) {
        this._taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> create(@PathVariable Long projectId, @Valid @RequestBody TaskModel taskModel, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            if (projectId == null) {
                response.setMessage(GeneralConstants.PROJECT_MANDATORY);
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return _taskService.createTask(taskModel, request);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
