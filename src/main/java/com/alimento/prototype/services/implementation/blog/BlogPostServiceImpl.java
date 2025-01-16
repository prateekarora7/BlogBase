package com.alimento.prototype.services.implementation.blog;

import com.alimento.prototype.dtos.blog.BlogPostRequest;
import com.alimento.prototype.entities.blog.BlogPost;
import com.alimento.prototype.entities.blog.ContentBlock;
import com.alimento.prototype.entities.blog.Tag;
import com.alimento.prototype.exceptions.SlugNotFoundException;
import com.alimento.prototype.mapper.ContentBlockRequestToContentBlock;
import com.alimento.prototype.repositories.blog.BlogPostRepository;
import com.alimento.prototype.repositories.blog.TagRepository;
import com.alimento.prototype.services.blog.BlogPostService;
import com.alimento.prototype.services.blog.ContentBlockService;
import com.alimento.prototype.utils.SlugUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    private final BlogPostRepository blogPostRepository;

    @Autowired
    private final ContentBlockService contentBlockService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SlugUtil slugUtil;

    @Autowired
    private EntityManager entityManager;


    public BlogPostServiceImpl(BlogPostRepository blogPostRepository, ContentBlockService contentBlockService) {
        this.blogPostRepository = blogPostRepository;
        this.contentBlockService = contentBlockService;
    }

    @Transactional
    @Override
    public void saveBlogPost(BlogPostRequest blogPostRequest) {

        List<Tag> allTags = blogPostRequest.getTags().stream().map(tag -> {    // This sets all the tag name to lowercase before operating
            tag.setTagName(tag.getTagName().toLowerCase());
            return tag;
        }).collect(Collectors.toList());

        Set<Tag> existingTags = new HashSet<>();  // This set stores already existing tags in the database
        Set<Tag> newTags = new HashSet<>();  // This set stores new tags, which are not in the database
        CompletableFuture<Void> existingTagsFuture = CompletableFuture.runAsync(() -> { // Using a new thread to match if the tag exists in database or not and will be added to the sets accordingly
            for (Tag tag : allTags) {
                if(tagRepository.findByTagName(tag.getTagName()) != null){
                    existingTags.add(tag);
                }
                else{
                    newTags.add(tag);
                }
            }
        });
        newTags.stream().forEach(tag -> tagRepository.save(tag));  //Saving all new tags to tag table, As ManyToMany relationship between Blogpost and Tag entity is Cascade.MERGE onlu

        String formattedSlug = slugUtil.toSlug(blogPostRequest.getTitle());  //generating slug from title and formatting the slug to lowercase and eliminating free space to make it url friendly
        String finalFormattedSlug = slugUtil.generateUniqueSlug(blogPostRepository.matchingBaseSlugs(formattedSlug+"%"), formattedSlug);  //Generating unique slugs, it will add number with hyphen at the end of slug if slug already exists


        List<ContentBlock> contentBlocks = blogPostRequest.getBlocks().stream() //Mapping Objects of ContentBlockRequest to ContentBlock manually using ContentBlockRequestToContentBlock class
                .map(request -> ContentBlockRequestToContentBlock.mapToEntity(request))
                .collect(Collectors.toList());

        BlogPost blogBuilder = BlogPost.builder() // Building a Blog Entity to save blog post to database
                .slug(finalFormattedSlug)
                .title(blogPostRequest.getTitle())
                .authorName(blogPostRequest.getAuthorName())
                .createdAt(LocalDateTime.now())
                .blocks(contentBlocks)
                .tags(blogPostRequest.getTags().stream().collect(Collectors.toSet()))
                .build();

        blogPostRepository.save(blogBuilder);
    }

    @Override
    public BlogPost getBlogPostbyId(long blogId) {
        BlogPost blogPost;
        try {
            blogPost = blogPostRepository.getBlogPostByBlogId(blogId);
        }catch (Exception e){
            throw new SlugNotFoundException("Blog post with this Blog Id does not exist : "+blogId);
        }
        return blogPost;
    }

    @Override
    public BlogPost getBlogPostBySlug(String slug) {
        BlogPost blogPost;
        try {
            blogPost = blogPostRepository.getBlogPostBySlug(slug);
        }catch (Exception e){
            throw new SlugNotFoundException("Blog post with this slug does not exist : "+slug);
        }
        return blogPost;
    }

    @Override
    public List<BlogPost> findByTags_TagName(String tagName) {
        return blogPostRepository.findByTags_TagName(tagName);
    }

    @Override
    public void deleteBySlug(String slug) {
        blogPostRepository.deleteBySlug(slug);
    }
}
