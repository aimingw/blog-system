package com.blog.controller.front;

import com.blog.common.Result;
import com.blog.entity.Category;
import com.blog.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台分类控制器
 * 提供公开访问的分类列表接口（无需登录）
 */
@RestController("frontCategoryController")
@RequestMapping("/api/front/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取全部分类列表
     */
    @GetMapping
    public Result<List<Category>> list() {
        return Result.success(categoryService.listAll());
    }
}
