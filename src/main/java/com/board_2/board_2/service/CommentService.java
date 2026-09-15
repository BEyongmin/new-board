package com.board_2.board_2.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board_2.board_2.entity.Comment;
import com.board_2.board_2.entity.Post;
import com.board_2.board_2.exception.PostNotFoundException;
import com.board_2.board_2.repository.CommentRepository;
import com.board_2.board_2.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional (readOnly = true)
    public List<Comment> getCommentList(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedDateAsc(postId);
    }

    @Transactional
    public Long createComment(Long postId, String author, String content){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
 
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setCreatedDate(LocalDate.now());
        comment.setPost(post);
 
        Comment createComment = commentRepository.save(comment);
 
        post.setReplyCount(post.getReplyCount() + 1);

        return createComment.getId();
    }
}
