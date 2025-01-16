package com.alimento.prototype.services.comment;

import com.alimento.prototype.dtos.comment.CommentRequest;
import com.alimento.prototype.entities.comment.Comment;

import java.util.List;

public interface CommentService {

    void saveComment(CommentRequest commentDTO);

    Comment getCommentByCommentId(long commentId);

    List<Comment> getCommentsBySlugAndUsername(String slug, String username);

    List<Comment> getCommentsByUsername(String userId);

    List<Comment> getCommentsBySlug(String slug);

    void deleteComment(long commentId);

    Comment updateComment(long commentId, String commentContent);
}
