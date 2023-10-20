package com.cdac.blog.dao;

import com.cdac.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

//dao is another name for repository  classes
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findPostById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Post st SET st.publishStatus=true WHERE st.id = :id1")
    void updatePublishedStatus(@Param("id1")Long id);

    @Transactional
    @Query(value = "FROM Post st WHERE st.publishStatus=true")
    List<Post> publishedPostsList();
}
