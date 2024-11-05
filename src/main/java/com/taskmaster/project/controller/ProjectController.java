package com.taskmaster.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.model.ProjectDTO;
import com.taskmaster.project.service.ProjectService;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.task.model.TaskRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private ProjectService _projectService;

    public ProjectController(ProjectService projectService) {
        this._projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> create(@Valid @RequestBody ProjectDTO projectDTO) {
        ResponseModel response = new ResponseModel();
        try {
            return _projectService.create(projectDTO);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseModel> getProject(@PathVariable Long projectId) {
        ResponseModel response = new ResponseModel();
        try {
            return _projectService.getProject(projectId);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ResponseModel> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectDTO projectDTO) {
        ResponseModel response = new ResponseModel();
        try {
            return _projectService.updateProject(projectId, projectDTO);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{projectId}/disable")
    public ResponseEntity<ResponseModel> disableProject(@PathVariable Long projectId) {
        ResponseModel response = new ResponseModel();
        try {
            return _projectService.disableProject(projectId);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
