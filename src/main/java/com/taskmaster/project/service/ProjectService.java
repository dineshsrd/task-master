package com.taskmaster.project.service;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.project.entity.Project;
import com.taskmaster.project.model.ProjectDTO;
import com.taskmaster.project.repository.ProjectRepository;
import com.taskmaster.project.util.ProjectUtil;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.user.util.UserUtil;

import jakarta.validation.Valid;

@Service
public class ProjectService {
    private ProjectRepository _projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this._projectRepository = projectRepository;
    }

    public ResponseEntity<ResponseModel> create(ProjectDTO projectDTO) {
        ResponseModel response = new ResponseModel();
        try {
            Project project = new Project();
            project.setName(projectDTO.getName());
            project.setKey(projectDTO.getKey());
            project.setUrl(projectDTO.getUrl());
            project.setDescription(projectDTO.getDescription());
            project.setIsActive(true);
            project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            project.setCreatedBy(UserUtil.getUserFromAuth());
            ProjectDTO createdProjectDTO = ProjectUtil.getProjectDto(_projectRepository.save(project));
            response.setMessage("Project created successfully");
            response.setStatus(HttpStatus.CREATED);
            response.setData(createdProjectDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseModel> getProject(Long projectId) {
        ResponseModel response = new ResponseModel();
        try {

            Project project = _projectRepository.findById(projectId).orElse(null);
            if (project == null) {
                response.setMessage("Project not found");
                response.setStatus(HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            ProjectDTO projectDTO = ProjectUtil.getProjectDto(project);
            response.setMessage("Project found");
            response.setStatus(HttpStatus.OK);
            response.setData(projectDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseModel> updateProject(Long projectId, @Valid ProjectDTO projectDTO) {
        ResponseModel response = new ResponseModel();
        try {
            Project project = _projectRepository.findById(projectId).
                    orElseThrow(() -> new RuntimeException("Project not found"));
            project.setName(projectDTO.getName());
            project.setUrl(projectDTO.getUrl());
            project.setDescription(projectDTO.getDescription());
            project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            ProjectDTO updatedProjectDTO = ProjectUtil.getProjectDto(_projectRepository.save(project));
            response.setMessage("Project updated successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(updatedProjectDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseModel> disableProject(Long projectId) {
        ResponseModel response = new ResponseModel();
        try {
            Project project = _projectRepository.findById(projectId).
                    orElseThrow(() -> new RuntimeException("Project not found"));
            project.setIsActive(false);
            ProjectDTO updatedProjectDTO = ProjectUtil.getProjectDto(_projectRepository.save(project));
            response.setMessage("Project disabled successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(updatedProjectDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
