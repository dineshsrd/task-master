package com.taskmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
