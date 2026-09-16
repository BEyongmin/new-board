package com.board_2.board_2.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board_2.board_2.entity.Comment;
import com.board_2.board_2.entity.Post;
import com.board_2.board_2.service.CommentService;
import com.board_2.board_2.service.PostService;

import lombok.RequiredArgsConstructor;


@Controller 
@RequestMapping 
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @RequestMapping ("/")
    public String home(@RequestParam(name = "keyword", required = false)  String keyword,
                    @RequestParam(name = "filter", required = false) String filter, 
                    @RequestParam(name = "sort",required = false) String sort, 
                    @RequestParam(name = "page", defaultValue = "1") int page,
                    Model model) {

            boolean noticeOnly = "notice".equals(filter);
            int pageIndex = page - 1;
            
            Page<Post> postPage = postService.searchPage(keyword, noticeOnly, sort, pageIndex);

            model.addAttribute("postList", postPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", postPage.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("filter", filter);
            model.addAttribute("sort", sort);

        return "posts/list";
    }

    // 1. 목록
    @RequestMapping("/posts")
    public String list(@RequestParam(name = "keyword", required = false)  String keyword,
                    @RequestParam(name = "filter", required = false) String filter, 
                    @RequestParam(name = "sort",required = false) String sort, 
                    @RequestParam(name = "page", defaultValue = "1") int page,
                            Model model) {

            boolean noticeOnly = "notice".equals(filter);
            int pageIndex = page - 1;
            
            Page<Post> postPage = postService.searchPage(keyword, noticeOnly, sort, pageIndex);

            model.addAttribute("postList", postPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", postPage.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("filter", filter);
            model.addAttribute("sort", sort);

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
    
    // 4. 게시글 상세 페이지
    @GetMapping("/posts/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Post post = postService.getDetail(id);
        List<Comment> commentList = commentService.getCommentList(id);

        model.addAttribute("post", post);
        model.addAttribute("commentList",commentList);
        return "posts/detail";
    }

    // 5. 수정 페이지
    @GetMapping ("/posts/{id}/edit")
    public String editForm(@PathVariable ("id") Long id, Model model){
        Post post = postService.getPostForEdit(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    
    @PostMapping ("/posts/{id}")
    public String update(@PathVariable ("id") Long id,
                        @RequestParam ("title") String title,
                        @RequestParam ("content") String content){
        
        Long updatedPostId = postService.update(id, title, content);
        return "redirect:/posts/"+ updatedPostId;
    }

    @PostMapping("/posts/{id}/delete")
    public String postMethodName(@PathVariable ("id") Long id) {
        postService.delete(id);
        
        return "redirect:/posts";
    }
    
}