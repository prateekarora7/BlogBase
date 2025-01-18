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

    @PostMapping("/update/{slug}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable String slug, @RequestBody BlogPostRequest blogPostRequest){
        BlogPost blogPost = blogPostService.updateBlogPost(slug, blogPostRequest);
        return new ResponseEntity<>(blogPost, HttpStatus.OK);
    }

    @GetMapping("/id/{blogId}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable long blogId){
        BlogPost blogPost = blogPostService.getBlogPostbyId(blogId);
        return new ResponseEntity<>(blogPost, HttpStatus.OK);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BlogPost> getBlogPostBySlug(@PathVariable String slug){
        BlogPost blogPost = blogPostService.getBlogPostBySlug(slug);
        return new ResponseEntity<>(blogPost, HttpStatus.OK);
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<BlogPost>> getPostsByTagName(@PathVariable String tagName){
        List<BlogPost> blogPosts = blogPostService.findByTags_TagName(tagName);
        return new ResponseEntity<>(blogPosts, HttpStatus.OK);
    }

    @DeleteMapping("delete/slug/{slug}")
    public ResponseEntity<Void> deleteBlogPostBySlug(@PathVariable String slug){
        blogPostService.deleteBySlug(slug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
