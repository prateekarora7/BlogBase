package com.alimento.prototype.controllers.comment;

import com.alimento.prototype.dtos.comment.CommentRequest;
import com.alimento.prototype.dtos.comment.UpdateCommentRequest;
import com.alimento.prototype.entities.comment.Comment;
import com.alimento.prototype.services.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
 
@RestController
@RequestMapping("/comment")
public class CommentController {

    //Creating a constructor injection for the comment service interface
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //This Post methods take comment DTO as body and maps it to comment DTO class.
    @PostMapping("/save")
    public void saveComment(@RequestBody CommentRequest commentRequest){
        commentService.saveComment(commentRequest);
    }

    //This method return comment after comparing comment Id
    @GetMapping("/id/{commentId}")
    public ResponseEntity<Comment> getCommentsByCommentId(@PathVariable Long commentId){
        return new ResponseEntity<>(commentService.getCommentByCommentId(commentId), HttpStatus.OK);
    }

    //This method return all the comments of a user in a list<Comment> after comparing username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Comment>> getCommentsByUserName(@PathVariable String username){
        return new ResponseEntity<>(commentService.getCommentsByUsername(username), HttpStatus.OK);
    }

    //This method return all the comments of a blog in a list<Comment> after comparing blog Id
    @GetMapping("/blog/{slug}")
    public ResponseEntity<List<Comment>> getCommentBySlug(@PathVariable String slug){
        return new ResponseEntity<>(commentService.getCommentsBySlug(slug), HttpStatus.OK);
    }

    //This method return list<Comment> after matching both blogId and Username
    @GetMapping("slug-username/{slug}/{username}")
    public ResponseEntity<List<Comment>> getCommentsByBlogIdAndUsername(@PathVariable String slug, @PathVariable String username){
        return new ResponseEntity<>(commentService.getCommentsBySlugAndUsername(slug, username), HttpStatus.OK);
    }

    //This delete method is removing the comment using the comment Id
    //If method returns Ok, then the request was successful. If it returns Internal server error, then the request was not successful.
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //This update method updates just the content of the comment
    @PostMapping("/update/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable long commentId, @RequestBody UpdateCommentRequest updateCommentRequest){
        commentService.updateComment(commentId, updateCommentRequest.getCommentContent());
        return new ResponseEntity<>(commentService.getCommentByCommentId(commentId), HttpStatus.CREATED);
    }

}
