package com.sb.blog.controller;

import com.sb.blog.payload.PostDto;
import com.sb.blog.payload.PostResponse;
import com.sb.blog.service.PostService;
import com.sb.blog.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Post controller rest apis")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post
    @ApiOperation(value = " Create new Post Rest Api")
    @PreAuthorize(("hasRole('ADMIN')"))
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts
    @ApiOperation(value = " Get All post")
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE, required = false) int page, @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_SIZE, required = false) int size,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
        return postService.getAllPosts(page, size, sortBy, sortDir);
    }

    @ApiOperation(value = "Get post by id")
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getpostById(id));
    }

    @ApiOperation(value = " update post")
    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete post")
    @PreAuthorize(("hasRole('ADMIN')"))
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>((postService.deletePost(id) ? "Post deleted Successfully." : "Post deletion failed."), HttpStatus.OK);
    }
}
