package com.cdac.blog.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_tags")
public class PostTags {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tag_id) {
        this.tagId = tag_id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long post_id) {
        this.postId = post_id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updatedAt = updated_at;
    }
}
