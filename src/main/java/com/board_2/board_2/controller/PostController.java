package com.board_2.board_2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board_2.board_2.entity.Post;
import com.board_2.board_2.service.PostService;

import lombok.RequiredArgsConstructor;



@Controller 
@RequestMapping 
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @RequestMapping ("/")
    public String home(Model model) {
        model.addAttribute(postService.getlist());
        return "posts/list";
    }

    // 1. 목록
    @RequestMapping("/posts")
    public String list(Model model) {
        model.addAttribute(postService.getlist());
        return "posts/list";
    }

    // 2. 작성 양식
    @GetMapping ("/posts/write")
    public String writeForm(Model model){
        return "posts/form";
    }
    
    // 3. 작성 양식 제출
    @PostMapping("/posts")
    public String write(@RequestParam ("title") String title, 
                        @RequestParam ("author") String author, 
                        @RequestParam ("content") String content) {
        Long newPostId = postService.create(title, author, content);
        return "redirect:/posts/"+newPostId;
    }
    
    // 3) 상세 
    @GetMapping("/posts/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Post post = postService.getDetail(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    










































 
    // 4) 수정- post를 넘기면 "글 수정" 모드
    @RequestMapping("/posts/{postId}/edit")
    public String editForm(@PathVariable("postId") String postId, Model model) {
        Map<String, Object> post = new HashMap<>();
        post.put("postId", postId);
        post.put("title", "게시판 미션 진행 중 막히는 부분 공유합니다");
        post.put("author", "작성자1");
        post.put("content", "목록 조회는 됐는데 페이지네이션에서 전체 건수를 어디서 받아야 하는지 헷갈렸습니다.");
 
        model.addAttribute("post", post);
        return "posts/form";
    }
}