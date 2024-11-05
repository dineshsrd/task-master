package com.taskmaster.status.util;

import com.taskmaster.status.entity.Status;
import com.taskmaster.status.model.StatusDTO;

public class StatusUtil {
    public static StatusDTO getStatusDto(Status status) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
        statusDTO.setDisplay_name(status.getDisplayName());
        return statusDTO;
    }
}
