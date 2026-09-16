package com.board_2.board_2.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board_2.board_2.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT p FROM Post p "
         + "WHERE (:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%') OR p.content LIKE CONCAT('%', :keyword, '%')) "
         + "AND (:noticeOnly = false OR p.notice = true) "
         + "ORDER BY p.notice DESC")
    Page<Post> search(@Param("keyword") String keyword,
                        @Param ("noticeOnly") boolean noticeOnly, Pageable pageable);
}
