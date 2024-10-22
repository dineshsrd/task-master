package com.taskmaster.status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmaster.status.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
