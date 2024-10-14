package com.taskmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
