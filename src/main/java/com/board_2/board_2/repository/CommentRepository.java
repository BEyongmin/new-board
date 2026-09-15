package com.board_2.board_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board_2.board_2.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
 
    List<Comment> findByPostIdOrderByCreatedDateAsc(Long postId);
    
    void deleteByPostId(Long postId);
}
