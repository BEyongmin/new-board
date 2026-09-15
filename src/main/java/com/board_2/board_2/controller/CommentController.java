package com.board_2.board_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board_2.board_2.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/posts/{id}/comments")
    public String create(@PathVariable("id") Long id,
                          @RequestParam("author") String author,
                          @RequestParam ("content")String content) {

        commentService.createComment(id, author, content);
        return "redirect:/posts/" + id;
    }
}