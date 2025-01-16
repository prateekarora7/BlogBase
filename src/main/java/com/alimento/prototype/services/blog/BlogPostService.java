package com.alimento.prototype.services.blog;

import com.alimento.prototype.dtos.blog.BlogPostRequest;
import com.alimento.prototype.entities.blog.BlogPost;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogPostService {

    void saveBlogPost(BlogPostRequest blogPostDTO);

    BlogPost getBlogPostbyId(long blogId);

    BlogPost getBlogPostBySlug(String slug);

    List<BlogPost> findByTags_TagName(@Param("tagName") String tagName);

    void deleteBySlug(@Param("slug") String slug);
}
