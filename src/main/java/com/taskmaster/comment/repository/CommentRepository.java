package com.taskmaster.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmaster.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.task.id = :taskId")
    Optional<List<Comment>> fetchCommentsByTaskId(@Param("taskId") Long taskId);
}
