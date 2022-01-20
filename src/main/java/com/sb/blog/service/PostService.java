package com.sb.blog.service;

import com.sb.blog.payload.PostDto;
import com.sb.blog.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int page, int size, String sortBy, String sortDir);

    PostDto getpostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    Boolean deletePost(Long id);

}
