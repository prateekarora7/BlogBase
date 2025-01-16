package com.alimento.prototype.dtos.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentRequest {

    private String commentContent;

    private String slug;

    private String username;

}
