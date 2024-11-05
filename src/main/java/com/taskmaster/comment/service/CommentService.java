package com.taskmaster.comment.service;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.taskmaster.comment.entity.Comment;
import com.taskmaster.comment.entity.CommentHistory;
import com.taskmaster.comment.model.CommentHistoryDTO;
import com.taskmaster.comment.repository.CommentHistoryRepository;
import com.taskmaster.comment.repository.CommentRepository;
import com.taskmaster.comment.util.CommentUtil;
import com.taskmaster.shared.model.response.ResponseModel;
import com.taskmaster.task.entity.Task;
import com.taskmaster.task.repository.TaskRepository;
import com.taskmaster.user.entity.User;
import com.taskmaster.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CommentService {

    private CommentRepository _commentRepository;
    private CommentHistoryRepository _commentHistoryRepository;
    private UserRepository _userRepository;
    private TaskRepository _taskRepository;

    public CommentService(CommentRepository commentRepository, CommentHistoryRepository commentHistoryRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this._commentRepository = commentRepository;
        this._commentHistoryRepository = commentHistoryRepository;
        this._userRepository = userRepository;
        this._taskRepository = taskRepository;
    }

    public ResponseEntity<ResponseModel> addComment(Long taskId, CommentHistoryDTO commentDTO, HttpServletRequest request) {
        ResponseModel response = new ResponseModel();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User commentUser = _userRepository.findByEmail(userName).orElseThrow(() -> new RuntimeException("User not found"));
            Task task = _taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

            Comment comment = new Comment();
            comment.setTask(task);
            comment.setUser(commentUser);
            _commentRepository.save(comment);

            CommentHistory commentHistory = new CommentHistory();
            commentHistory.setComment(comment);
            commentHistory.setCommentText(commentDTO.getContent());
            commentHistory.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commentHistory.setUser(commentUser);
            commentHistory.setEdited(false);
            CommentHistory commentResponse = _commentHistoryRepository.save(commentHistory);

            response.setMessage("Comment added successfully");
            response.setStatus(HttpStatus.OK);
            response.setData(CommentUtil.getCommentDto(commentResponse));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
