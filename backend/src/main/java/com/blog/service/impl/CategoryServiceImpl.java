package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.BusinessException;
import com.blog.entity.Article;
import com.blog.entity.Category;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, ArticleMapper articleMapper) {
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    /**
     * 查询所有分类（按创建时间倒序）
     */
    @Override
    public List<Category> listAll() {
        return categoryMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByDesc(Category::getCreateTime));
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    /**
     * 新增或更新分类
     * 校验分类名称不能重复
     */
    @Override
    public void saveOrUpdate(Category category) {
        // 检查同名分类是否已存在
        Category exist = categoryMapper.selectOne(
                new LambdaQueryWrapper<Category>().eq(Category::getName, category.getName()));
        if (exist != null && !exist.getId().equals(category.getId())) {
            throw new BusinessException("Category name already exists");
        }
        if (category.getId() == null) {
            categoryMapper.insert(category);
        } else {
            categoryMapper.updateById(category);
        }
    }

    /**
     * 删除分类
     * 若该分类下还有文章则不允许删除
     */
    @Override
    public void deleteById(Long id) {
        Long count = articleMapper.selectCount(
                new LambdaQueryWrapper<Article>().eq(Article::getCategoryId, id));
        if (count > 0) {
            throw new BusinessException("Category has articles, cannot delete");
        }
        categoryMapper.deleteById(id);
    }
}
