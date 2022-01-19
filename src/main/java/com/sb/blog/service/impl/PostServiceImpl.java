package com.sb.blog.service.impl;

import com.sb.blog.entity.Post;
import com.sb.blog.exception.ResourceNotFoundException;
import com.sb.blog.payload.PostDto;
import com.sb.blog.payload.PostResponse;
import com.sb.blog.repository.PostRepository;
import com.sb.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        //Convert entity to DTO
        PostDto postResponse = mapToDto(post);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int page, int size, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(page, size, (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()));
        Page<Post> allPosts = postRepository.findAll(pageable);
        List<PostDto> listDto = allPosts.getContent().stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listDto);
        postResponse.setPage(allPosts.getNumber());
        postResponse.setSize(allPosts.getSize());
        postResponse.setTotalPage(allPosts.getTotalPages());
        postResponse.setTotalElements(allPosts.getTotalElements());
        postResponse.setLast(allPosts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getpostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id + ""));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id + ""));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return mapToDto(postRepository.save(post));
    }

    @Override
    public Boolean deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(id)));
        postRepository.delete(post);
        return true;
    }

    //convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    private PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
}
