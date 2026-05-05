package com.blog.service;

import com.blog.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> listAll();
    Category getById(Long id);
    void saveOrUpdate(Category category);
    void deleteById(Long id);
}
