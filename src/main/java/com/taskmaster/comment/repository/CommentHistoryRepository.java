package com.taskmaster.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmaster.comment.entity.CommentHistory;

public interface CommentHistoryRepository extends JpaRepository<CommentHistory, Long> {
    @Query("SELECT ch FROM CommentHistory ch WHERE ch.comment.id = :commentId")
    Optional<List<CommentHistory>> fetchCommentHistoryByCommentId(@Param("commentId") Long commentId);
}


