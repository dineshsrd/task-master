package com.taskmaster.comment.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.taskmaster.comment.entity.Comment;
import com.taskmaster.comment.entity.CommentHistory;
import com.taskmaster.comment.model.CommentDto;
import com.taskmaster.comment.model.CommentHistoryDTO;
import com.taskmaster.user.util.UserUtil;

public class CommentUtil {
    public static CommentHistoryDTO getCommentDto(CommentHistory commentHistory) {
        CommentHistoryDTO commentDTO = new CommentHistoryDTO();
        commentDTO.setContent(commentHistory.getCommentText());
        commentDTO.setCreatedAt(commentHistory.getCreatedAt());
        commentDTO.setEdited(commentHistory.isEdited());
        commentDTO.setUser(UserUtil.getUserDTO(commentHistory.getUser()));
        return commentDTO;
    }

    public static Set<CommentDto> getAllComments(Set<Comment> commentHistory) {
        if (commentHistory == null) {
            return new HashSet<>();
        }
        return commentHistory.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setUser(UserUtil.getUserDTO(comment.getUser()));
            commentDto.setComment(getCommentDto(comment.getHistory()));
            return commentDto;
        }).collect(Collectors.toSet());
    }

    public static Set<CommentHistoryDTO> getCommentDto(Set<CommentHistory> commentHistory) {
        if (commentHistory == null) {
            return new HashSet<>();
        }
        return commentHistory.stream().map(comment -> {
            CommentHistoryDTO commentDto = new CommentHistoryDTO();
            commentDto.setContent(comment.getCommentText());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setEdited(comment.isEdited());
            commentDto.setUser(UserUtil.getUserDTO(comment.getUser()));
            return commentDto;
        }).collect(Collectors.toSet());
    }
}
