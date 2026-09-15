package com.board_2.board_2.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board_2.board_2.entity.Post;
import com.board_2.board_2.exception.PostNotFoundException;
import com.board_2.board_2.repository.Postrepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PostService {

    private final Postrepository postrepository;

    // 1. 목록
    @Transactional (readOnly = true)
    public List<Post> getlist() {
        return postrepository.findAllOrderByNoticeAndCreatedDateDesc();
    }

    // 2. 등록
    @Transactional
    public Long create(String title ,String author, String contect){
        Post post = new Post();
        post.setTitle(title);
        post.setAuthor(author);
        post.setContent(contect);
        post.setCreatedDate(LocalDate.now());
        post.setViewCount(0L);
        post.setReplyCount(0L);
        post.setNotice(false);

        Post savePost = postrepository.save(post);
        return savePost.getId();
    }

    @Transactional 
    public Post getDetail(Long id){
        Post post = postrepository.findById(id)
        .orElseThrow(()-> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        post.setViewCount(post.getViewCount()+1);
        return post;
    }
}
