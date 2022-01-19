package com.sb.blog.service;

import com.sb.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getpostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    Boolean deletePost(Long id);

}
