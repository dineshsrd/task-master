package com.taskmaster.status.entity;

import java.util.ArrayList;
import java.util.List;

import com.taskmaster.task.entity.Task;
import com.taskmaster.status.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private StatusEnum name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "is_default")
    private boolean isDefault;

    @OneToMany(mappedBy = "status")
    private List<Task> tasks = new ArrayList<>();
}
