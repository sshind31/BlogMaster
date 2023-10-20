package com.cdac.blog.dao;

import com.cdac.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//dao is another name for repository  classes
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByName(String theTag);
}
