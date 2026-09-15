package com.board_2.board_2.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Comment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column 
    private String author;

    @Column (length = 500, nullable = false)
    private String content;

    @Column 
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}