package com.sb.blog.service;

import com.sb.blog.payload.CommentDto;

import java.util.List;


public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentsByPostIdAndId(Long postId, Long id);

    CommentDto updateComment(Long postId, Long id, CommentDto commentDto);

    boolean deleteComment(Long postId, Long id);
}
