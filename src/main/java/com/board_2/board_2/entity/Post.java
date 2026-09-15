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
    private Long id;

    @Column (length = 300, nullable = false)
    private String title;
    
    @Column (length = 300, nullable = false)
    private String content;

    @Column (nullable = false)
    private String author;

    @Column
    private LocalDate createdDate;

    @Column
    private Long viewCount;

    @Column
    private Long replyCount;

    @Column
    private Boolean notice;
}