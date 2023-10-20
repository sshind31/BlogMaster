package com.cdac.blog.dao;


import com.cdac.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
// dao is another name for repository  classes
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
