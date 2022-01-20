package com.sb.blog.service.impl;

import com.sb.blog.entity.Comment;
import com.sb.blog.entity.Post;
import com.sb.blog.exception.BlogApiException;
import com.sb.blog.exception.ResourceNotFoundException;
import com.sb.blog.payload.CommentDto;
import com.sb.blog.repository.CommentRepository;
import com.sb.blog.repository.PostRepository;
import com.sb.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("POST", "ID", postId + ""));
        comment.setPost(post);
        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
//        if(commentList.size()==0){
//            throw new ResourceNotFoundException("COMMENT", "POST_ID", postId + "");
//        }
        List<CommentDto> commentDtoList = commentList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        return commentDtoList;
    }

    @Override
    public CommentDto getCommentsByPostIdAndId(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("POST", "ID", postId + ""));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("COMMENT", "ID", id + ""));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post.");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long id, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("POST", "ID", postId + ""));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("COMMENT", "ID", id + ""));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post.");
        }
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public boolean deleteComment(Long postId, Long id) {
        Comment comment = commentRepository.findAllByPostIdAndId(postId, id).orElseThrow(() -> new ResourceNotFoundException("POST/COMMENT", "ID", postId + "/" + id));
        commentRepository.delete(comment);
        return true;
    }


    private CommentDto mapToDto(Comment comment) {

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
//        commentDto.setId(comment.getId());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
        return comment;
    }
}


