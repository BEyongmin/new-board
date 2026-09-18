package com.board_2.board_2.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor 
@Getter
@Setter
@NoArgsConstructor 
public class Post {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 번호

    @Column(length = 100, nullable = false)
    private String title; // 게시글 제목
    
    @Column(length = 2000, nullable = false)
    private String content; // 게시글 내용

    @Column (length = 20, nullable = false)
    private String author; // 게시글 작성자

    @Column
    private LocalDate createdDate;

    @Column
    private Long viewCount;

    @Column
    private Long replyCount;

    @Column
    private Boolean notice;
}