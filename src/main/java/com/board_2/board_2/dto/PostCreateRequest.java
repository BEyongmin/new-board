package com.board_2.board_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 20, message = "닉네임은 20자 이내로 입력해주세요.")
    private String author;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 2000, message = "내용은 2000자 이내로 입력해주세요.")
    private String content;
}