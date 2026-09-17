package com.board_2.board_2.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board_2.board_2.entity.Post;
import com.board_2.board_2.exception.PostNotFoundException;
import com.board_2.board_2.repository.CommentRepository;
import com.board_2.board_2.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    
    // 1. 목록
    @Transactional (readOnly = true)
    public Page<Post> searchPage(String keyword, boolean noticeOnly, String sort, int page){
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

    Sort sortOption = "view".equals(sort)
            ? Sort.by(Sort.Direction.DESC, "viewCount").and(Sort.by(Sort.Direction.DESC, "id"))
            : Sort.by(Sort.Direction.DESC, "createdDate").and(Sort.by(Sort.Direction.DESC, "id"));
        
        Pageable pageable = PageRequest.of(page, 10, sortOption);

        return postRepository.search(keyword, noticeOnly, pageable);
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

        Post savePost = postRepository.save(post);
        return savePost.getId();
    }

    @Transactional 
    public Post getDetail(Long id){
        Post post = postRepository.findById(id)
        .orElseThrow(()-> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        post.setViewCount(post.getViewCount()+1);
        return post;
    }

    @Transactional (readOnly = true)
    public Post getPostForEdit(Long id) {
        return postRepository.findById(id)
                    .orElseThrow(()-> new PostNotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Transactional 
    public Long update(Long id, String title, String content){
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("일시적인 오류로 업데이트가 불가능합니다."));

        post.setTitle(title);
        post.setContent(content);
        
        return post.getId();
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        commentRepository.deleteByPostId(id);
        postRepository.delete(post);
    }
}
