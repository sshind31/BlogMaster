package com.cdac.blog.service;

import com.cdac.blog.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAllTags();
}
