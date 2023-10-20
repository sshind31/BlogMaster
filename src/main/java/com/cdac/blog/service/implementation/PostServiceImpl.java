package com.cdac.blog.service.implementation;

import com.cdac.blog.dao.*;
import com.cdac.blog.entity.Post;
import com.cdac.blog.entity.Tag;
import com.cdac.blog.pagination.Pagination;
import com.cdac.blog.service.PostService;
import com.cdac.blog.specification.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostSpecification postSpecification;
    private final Pagination pagination;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostServiceImpl(PostRepository thePostRepository, PostSpecification thePostSpecification, Pagination thePagination, PostTagRepository thePostTagRepository, TagRepository theTagRepository) {
        postRepository = thePostRepository;
        postSpecification = thePostSpecification;
        pagination = thePagination;
        postTagRepository = thePostTagRepository;
        tagRepository = theTagRepository;
    }


    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public void savePost(Post thePost) {
        postRepository.save(createPostWithTags(thePost));
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void updatePublishedStatus(Long id) {
    	
        postRepository.updatePublishedStatus(id);
    }

    @Override
    public Page<Post> findFilteredPostPaged(String start, String limit, Optional<String> publishedStart, Optional<String> publishedEnd, Optional<String> order, Optional<String> search, Optional<List<Long>> tagId, Optional<List<Long>> authorId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return postRepository.findAll(postSpecification.search(search.isPresent()?search.get():null)
                                    .and(postSpecification.filterPostByTagIdList(tagId.isPresent()?tagId.get():null))
                                    .and(postSpecification.filterPostByAuthorIdList(authorId.isPresent()?authorId.get():null))
                                    .and(postSpecification.filterPostAfter(publishedStart.isPresent()?LocalDateTime.parse(publishedStart.get()+" 00:00",formatter):null))
                                    .and(postSpecification.filterPostBefore(publishedEnd.isPresent()?LocalDateTime.parse(publishedEnd.get()+" 00:00",formatter):null)),
                                    pagination.getPageable(Integer.parseInt(start)/10,Integer.parseInt(limit),"publishedAt",order)
                                    );
    }

    @Override
    public Page<Post> findFilteredPostHomePaged(String start, String limit, Optional<String> publishedStart, Optional<String> publishedEnd, Optional<String> order, Optional<String> search, Optional<List<Long>> tagId, Optional<List<Long>> authorId, Long userId) {
        List<Long> authorIds = authorId.isPresent()?authorId.get():null;
        if(authorIds == null) {
            authorIds = new ArrayList<>();
            authorIds.add(userId);
        } else {
            authorIds.add(userId);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return postRepository.findAll(postSpecification.search(search.isPresent()?search.get():null)
                        .and(postSpecification.filterPostByTagIdList(tagId.isPresent()?tagId.get():null))
                        .and(postSpecification.filterPostByAuthorIdList(authorIds))
                        .and(postSpecification.filterPostAfter(publishedStart.isPresent()?LocalDateTime.parse(publishedStart.get()+" 00:00",formatter):null))
                        .and(postSpecification.filterPostBefore(publishedEnd.isPresent()?LocalDateTime.parse(publishedEnd.get()+" 00:00",formatter):null)),
                pagination.getPageable(Integer.parseInt(start)/10,Integer.parseInt(limit),"publishedAt",order)
        );
    }

    private Post createPostWithTags(Post thePost) {
        String[] tagsString = thePost.getTagsInString().split("#");
        List<Tag> tagList = thePost.getTags();
        // removing the tags which are present in Post tags but not present in tagsString
        if (tagList != null) {
            for(Tag theTag: tagList){
                boolean tagIsPresent = false;
                for(String tag: tagsString) {
                    if(tag.trim().length() > 0 && !tag.trim().isEmpty()) {
                        if (theTag.getName().equals(tag.trim())) {
                            tagIsPresent = true;
                            break;
                        }
                    }
                }
                if (tagIsPresent) {
                    postTagRepository.deletePostTagsByPostIdAndTagId(thePost.getId(),theTag.getId());
                }
            }
        }
        // adding the tags which are present in tagsString but not in Post tags
        for(String tag: tagsString) {
            if(tag.trim().length() > 0 && !tag.trim().isEmpty()) {
                Optional<Tag> optionalTag = tagRepository.findTagByName(tag.trim());
                if (optionalTag.isPresent()) {
                    Tag theTag = optionalTag.get();
                    thePost.addTag(theTag);
                } else {
                    Tag theTag = new Tag();
                    theTag.setName(tag.trim());
                    thePost.addTag(theTag);
                }
            }
        }
        return thePost;
    }
    //Admin code
    public List<Post> getAllPublishedPosts()
    {
    	System.out.println("Service getting request");
    	List<Post> Posts=postRepository.publishedPostsList();
    	return Posts;
    }
}
