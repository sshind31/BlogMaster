package com.cdac.blog.service;

import com.cdac.blog.entity.Comment;

import java.util.Optional;

public interface CommentService {
    Optional<Comment> findCommentById(Long id);
    void deleteCommentById(Long commentId);
    void saveComment(Comment theComment);
}
