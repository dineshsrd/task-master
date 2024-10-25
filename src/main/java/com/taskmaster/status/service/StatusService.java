package com.taskmaster.status.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taskmaster.status.entity.Status;
import com.taskmaster.status.repository.StatusRepository;

@Service
public class StatusService {
    private StatusRepository _statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this._statusRepository = statusRepository;
    }

    public List<Status> getAllStatus() {
        return _statusRepository.findAll();
    }
}
