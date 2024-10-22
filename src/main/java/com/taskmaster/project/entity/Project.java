package com.taskmaster.project.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.taskmaster.task.entity.Task;

import jakarta.persistence.CascadeType;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents recursion with ProjectUserRole
    private Set<ProjectUserRole> projectUserRoles = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();
}
