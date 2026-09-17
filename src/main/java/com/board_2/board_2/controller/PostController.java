package com.board_2.board_2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.board_2.board_2.dto.PostCreateRequest;
import com.board_2.board_2.dto.PostUpdateRequest;
import com.board_2.board_2.entity.Comment;
import com.board_2.board_2.entity.Post;
import com.board_2.board_2.exception.PostNotFoundException;
import com.board_2.board_2.service.CommentService;
import com.board_2.board_2.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller 
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    
    // 1. 목록
    @RequestMapping({"/","/posts"})
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
    @GetMapping("/posts/write")
    public String writeForm(Model model) {
        model.addAttribute("isEdit", false);
        return "posts/form";
    }
    
    // 3. 작성 양식 제출
    @PostMapping("/posts")
    public String write(@Valid @ModelAttribute("post") PostCreateRequest form,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            return "posts/form";
        }

        Long newPostId = postService.create(form.getTitle(), form.getAuthor(), form.getContent());
        return "redirect:/posts/" + newPostId;
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
    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostForEdit(id);
        model.addAttribute("post", post);
        model.addAttribute("postId", id);
        model.addAttribute("isEdit", true);
        return "posts/form";
    }

    
    @PostMapping("/posts/{id}")
    public String update(@PathVariable("id") Long id,
                        @Valid @ModelAttribute("post") PostUpdateRequest form,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("isEdit", true);
            model.addAttribute("postId", id);
            return "posts/form";
        }

        Long updatedPostId = postService.update(id, form.getTitle(), form.getAuthor(), form.getContent());
        return "redirect:/posts/" + updatedPostId;
    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable ("id") Long id) {
        postService.delete(id);
        
        return "redirect:/posts";
    }
    
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePostNotFound(PostNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "posts/not-found";
    }
}
