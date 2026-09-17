package com.board_2.board_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board_2.board_2.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/posts/{id}/comments")
    public String create(@PathVariable("id") Long id,
                          @RequestParam("author") @NotBlank(message = "닉네임을 입력해주세요.") @Size(max = 20, message = "닉네임은 20자 이내로 입력해주세요.") String author,
                          @RequestParam("content") @NotBlank(message = "댓글 내용을 입력해주세요.") @Size(max = 500, message = "댓글은 500자 이내로 입력해주세요.") String content) {

        commentService.createComment(id, author, content);
        return "redirect:/posts/" + id;
    }

    @ExceptionHandler({HandlerMethodValidationException.class, ConstraintViolationException.class})
    public String handleValidationError(Exception ex,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes) {

        String requestUri = request.getRequestURI();
        String postId = requestUri.split("/")[2];

        redirectAttributes.addFlashAttribute("commentError", "댓글 내용을 확인해주세요.");
        return "redirect:/posts/" + postId;
    }
}