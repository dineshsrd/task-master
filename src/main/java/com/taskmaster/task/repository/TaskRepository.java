package com.taskmaster.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
