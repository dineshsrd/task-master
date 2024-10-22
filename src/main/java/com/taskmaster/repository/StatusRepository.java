package com.taskmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmaster.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
