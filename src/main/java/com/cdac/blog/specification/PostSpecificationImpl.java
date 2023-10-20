package com.cdac.blog.specification;

import com.cdac.blog.entity.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostSpecificationImpl implements PostSpecification {
    @Override
    public Specification<Post> search(String keyword) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (keyword == null) {
                return null;
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" +keyword+ "%"),
                    criteriaBuilder.like(root.get("context"), "%" +keyword+ "%"),
                    criteriaBuilder.like(root.get("excerpt"), "%" +keyword+ "%"),
                    criteriaBuilder.like(root.get("author"), "%" +keyword+ "%"),
                    criteriaBuilder.like(root.join("tags").get("name"),"%" +keyword.trim().toUpperCase()+ "%")
                    );
        });
    }

    @Override
    public Specification<Post> filterPostByAuthorIdList(List<Long> authorIds) {
        if (authorIds == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            criteriaQuery.distinct(true);
            for (long authorId:authorIds) {
                predicates.add(criteriaBuilder.equal(root.join("user").get("id"), authorId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    public Specification<Post> filterPostByTagIdList(List<Long> tagIds) {
        if (tagIds == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> { criteriaQuery.distinct(true);
           List<Predicate> predicates = new ArrayList<>();
            criteriaQuery.distinct(true);
           for (long tagId:tagIds) {
               predicates.add(criteriaBuilder.equal(root.join("tags").get("id"), tagId));
           }
           return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    public Specification<Post> filterPostAfter(LocalDateTime startDate) {
        if (startDate == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("publishedAt"), startDate));
    }

    @Override
    public Specification<Post> filterPostBefore(LocalDateTime endDate) {
        if (endDate == null) {
            return null;
        }
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("publishedAt"), endDate));
    }
}
