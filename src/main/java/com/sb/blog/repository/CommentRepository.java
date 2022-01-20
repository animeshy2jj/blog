package com.sb.blog.repository;

import com.sb.blog.entity.Comment;
import com.sb.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findAllByPostId(Long postId);
    public Optional<Comment> findAllByPostIdAndId(Long postId, Long id);
    public boolean deleteByPostIdAndId(Long postId, Long id);
}
