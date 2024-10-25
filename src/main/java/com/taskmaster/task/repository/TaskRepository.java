package com.taskmaster.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmaster.status.entity.Status;
import com.taskmaster.task.entity.Task;
import com.taskmaster.user.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId")
    Optional<List<Task>> fetchTasksByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.status.id = :statusId")
    Optional<List<Task>> fetchByStatus(@Param("statusId") Long statusId);

    @Query("SELECT t FROM Task t WHERE t.summary like %:searchKey% or t.description like %:searchKey%")
    Optional<List<Task>> search(@Param("searchKey") String searchKey);
}
