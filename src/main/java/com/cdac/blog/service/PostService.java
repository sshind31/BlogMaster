package com.cdac.blog.service;

import com.cdac.blog.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> findPostById(Long id);
    void savePost(Post thePost);
    void deletePostById(Long id);
    void updatePublishedStatus(Long id);
    Page<Post> findFilteredPostPaged(String start, String limit, Optional<String> publishedStart, Optional<String> publishedEnd, Optional<String> order, Optional<String> search, Optional<List<Long>> tagId, Optional<List<Long>> authorId);
    Page<Post> findFilteredPostHomePaged(String start, String limit, Optional<String> publishedStart, Optional<String> publishedEnd, Optional<String> order, Optional<String> search, Optional<List<Long>> tagId, Optional<List<Long>> authorId, Long userId);
  //Admin code
    List<Post> getAllPublishedPosts();
    
}
