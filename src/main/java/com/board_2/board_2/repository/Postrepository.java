package com.board_2.board_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.board_2.board_2.entity.Post;

public interface  Postrepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT p FROM Post p ORDER BY p.notice DESC, p.createdDate DESC")
    List<Post> findAllOrderByNoticeAndCreatedDateDesc();
}
