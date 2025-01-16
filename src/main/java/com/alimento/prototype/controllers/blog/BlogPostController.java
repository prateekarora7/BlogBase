package com.alimento.prototype.controllers.blog;

import com.alimento.prototype.dtos.blog.BlogPostRequest;
import com.alimento.prototype.entities.blog.BlogPost;
import com.alimento.prototype.services.blog.BlogPostService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/save")
    public ResponseEntity saveBlogPost(@RequestBody BlogPostRequest blogPostRequest){

        //saving the auto generated blog Id in a variable
        blogPostService.saveBlogPost(blogPostRequest);

        // returning the blogId with the response entity
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/id/{blogId}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable long blogId){
        return new ResponseEntity<>(blogPostService.getBlogPostbyId(blogId), HttpStatus.OK);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BlogPost> getBlogPostBySlug(@PathVariable String slug){
        return new ResponseEntity<>(blogPostService.getBlogPostBySlug(slug), HttpStatus.OK);
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<BlogPost>> getPostsByTagName(@PathVariable String tagName){
        return new ResponseEntity<>(blogPostService.findByTags_TagName(tagName), HttpStatus.OK);
    }

    @DeleteMapping("delete/slug/{slug}")
    public ResponseEntity<Void> deleteBlogPostBySlug(@PathVariable String slug){
        blogPostService.deleteBySlug(slug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
