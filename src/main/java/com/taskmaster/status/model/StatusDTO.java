package com.taskmaster.status.model;

import com.taskmaster.status.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatusDTO {
    private Long id;
    private StatusEnum name;
    private String display_name;
}
