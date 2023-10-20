package com.cdac.blog.service.implementation;

import com.cdac.blog.dao.*;
import com.cdac.blog.entity.Comment;
import com.cdac.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository theCommentRepository) {
        commentRepository = theCommentRepository;
    }

    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void saveComment(Comment theComment) {
        commentRepository.save(theComment);
    }
}
