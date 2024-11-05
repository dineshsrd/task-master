package com.taskmaster.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.taskmaster.user.entity.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User changedBy;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_display_name")
    private String fieldDisplayName;

    @Column(name = "from_value")
    private String fromValue;

    @Column(name = "to_value")
    private String toValue;

}

