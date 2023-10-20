package com.cdac.blog.dao;

import com.cdac.blog.entity.PostTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
//dao is another name for repository  classes
public interface PostTagRepository extends JpaRepository<PostTags, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM PostTags st WHERE st.postId=?1 and st.tagId=?2")
    void deletePostTagsByPostIdAndTagId(Long thePostId, Long theTagId);
}
