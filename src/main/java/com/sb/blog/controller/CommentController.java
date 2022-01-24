package com.sb.blog.controller;

import com.sb.blog.payload.CommentDto;
import com.sb.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{post_id}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "post_id") Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{post_id}/comment")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "post_id") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{post_id}/comment/{id}")
    public ResponseEntity<CommentDto> getCommentsByPostIdAndId(@PathVariable(value = "post_id") Long postId, @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(commentService.getCommentsByPostIdAndId(postId, id), HttpStatus.OK);
    }

    @PutMapping("/posts/{post_id}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "post_id") Long postId, @PathVariable(value = "id") Long id, @Valid @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.updateComment(postId, id, commentDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{post_id}/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "post_id") Long postId, @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(commentService.deleteComment(postId, id) ? "Comment with ID:" + id + " successfully deleted." : "Comment does not exist.", HttpStatus.OK);
    }
}
