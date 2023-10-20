package com.cdac.blog.specification;

import com.cdac.blog.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public interface PostSpecification {
    Specification<Post> search(String keyword);
    Specification<Post> filterPostByAuthorIdList(List<Long> authorIds);
    Specification<Post> filterPostByTagIdList(List<Long> tagIds);
    Specification<Post> filterPostAfter(LocalDateTime startDate);
    Specification<Post> filterPostBefore(LocalDateTime endDate);
}
