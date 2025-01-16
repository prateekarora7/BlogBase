package com.alimento.prototype.repositories.comment;

import com.alimento.prototype.entities.comment.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //Abstract method to save a newly posted comment
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO comment (comment_content, slug, username, comment_date) VALUES (:commentContent, :slug, :username, :commentDate)", nativeQuery = true)
    void saveComment(@Param("commentContent") String commentContent, @Param("slug") String slug, @Param("username") String username, @Param("commentDate") LocalDate commentDate);

    //Method to get comment by Comment Id
    @Query(value = "SELECT * FROM comment WHERE comment_id = :commentId", nativeQuery = true)
    Comment getCommentByCommentId(@Param("commentId") long commentId);

    //Method to get List of comments by Username or comments done by an user
    @Query(value = "SELECT * FROM comment WHERE username = :username", nativeQuery = true)
    List<Comment> getCommentsByUsername(@Param("username") String username);

    //Method to get list of comments by blog Id or comments on an blog
    @Query(value = "SELECT * FROM comment WHERE slug = :slug", nativeQuery = true)
    List<Comment> getCommentsBySlug(@Param("slug") String slug);

    //Method to get list of comments by slug and Username
    @Query(value = "SELECT * FROM comment WHERE slug = :slug AND username = :username", nativeQuery = true)
    List<Comment> getCommentsBySlugAndUsername(@Param("slug") String slug, @Param("username") String username);

    //Method to delete the comment using comment id
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment WHERE comment_id = :commentId", nativeQuery = true)
    void deleteComment(@Param("commentId") long commentId);

    //Method to update comment
    @Transactional
    @Modifying
    @Query(value = "UPDATE comment SET comment_content = :commentContent WHERE comment_id = :commentId", nativeQuery = true)
    void updateComment(@Param("commentId") long commentId, @Param("commentContent") String commentContent);
}
