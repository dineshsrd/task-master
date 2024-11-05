package com.taskmaster.comment.model;

import java.sql.Timestamp;

import com.taskmaster.user.model.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentHistoryDTO {
    private String content;
    private Timestamp createdAt;
    private boolean isEdited;
    private UserDTO user;

}
