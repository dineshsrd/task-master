package com.taskmaster.comment.model;

import java.util.Set;

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
public class CommentDto {
    private Long id;
    private UserDTO user;
    private Set<CommentHistoryDTO> comment;
}
