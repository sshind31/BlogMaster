package com.cdac.blog.service.implementation;

import com.cdac.blog.dao.TagRepository;
import com.cdac.blog.entity.Tag;
import com.cdac.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Autowired
    public TagServiceImpl(TagRepository theTagRepository ) {
        tagRepository = theTagRepository;
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }
}
