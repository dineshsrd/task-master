package com.taskmaster.status.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.status.entity.Status;
import com.taskmaster.status.model.StatusDTO;
import com.taskmaster.status.service.StatusService;
import com.taskmaster.task.model.TaskRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private StatusService _statusService;

    public StatusController(StatusService statusService) {
        this._statusService = statusService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResponseModel> fetchAll() {
        ResponseModel response = new ResponseModel();
        try {
            List<StatusDTO> allStatus = _statusService.getAllStatus().stream().map(status -> {
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setId(status.getId());
                statusDTO.setName(status.getName());
                statusDTO.setDisplay_name(status.getDisplayName());
                return statusDTO;
            }).toList();
            response.setData(allStatus);
            response.setMessage("All Status fetched successfully");
            response.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
