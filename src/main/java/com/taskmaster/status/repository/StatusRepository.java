package com.taskmaster.status.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmaster.status.entity.Status;
import com.taskmaster.status.enums.StatusEnum;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAllByIsDefaultTrue();
    Optional<Status> findById(Long id);
    Optional<Status> findByName(StatusEnum name);
}
