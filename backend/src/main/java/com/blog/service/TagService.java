package com.blog.service;

import com.blog.entity.Tag;
import java.util.List;

public interface TagService {
    List<Tag> listAll();
    Tag getById(Long id);
    void saveOrUpdate(Tag tag);
    void deleteById(Long id);
}
