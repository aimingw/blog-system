package com.blog.service;

import com.blog.entity.Category;
import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {
    /** 查询所有分类 */
    List<Category> listAll();
    /** 根据ID查询分类 */
    Category getById(Long id);
    /** 新增或更新分类 */
    void saveOrUpdate(Category category);
    /** 根据ID删除分类 */
    void deleteById(Long id);
}
