package com.taskmaster.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import com.taskmaster.task.entity.Task;
import com.taskmaster.user.entity.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Original author of the comment

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommentHistory> history; // All versions (original and edits)

}