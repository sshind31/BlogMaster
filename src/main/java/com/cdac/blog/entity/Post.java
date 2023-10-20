package com.cdac.blog.entity;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "excerpt",columnDefinition="TEXT")
    private String excerpt;

    @Column(name = "context",columnDefinition="TEXT")
    private String context;

    @UpdateTimestamp
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "is_published")
    private boolean publishStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="post_id")
    private List<Comment> comments;

    @Column(name="author")
    private String author;

    @ManyToMany(fetch=FetchType.LAZY,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="post_tags",
            joinColumns=@JoinColumn(name="post_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id")
    )
    private List<Tag> tags;

    private String tagsInString;

    public String getTagsInString() {
        return tagsInString;
    }

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTagsInString(String tagsInString) {
        this.tagsInString = tagsInString;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(boolean publishStatus) {
        this.publishStatus = publishStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // convenience method
    public void addComment(Comment theComment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(theComment);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    // convenience method
    public void addTag(Tag theTag){
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(theTag);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", context='" + context + '\'' +
                ", publishedAt=" + publishedAt +
                ", publishStatus=" + publishStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", comments=" + comments +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                ", tagsInString='" + tagsInString + '\'' +
                ", user=" + user +
                '}';
    }
}
