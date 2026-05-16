package com.blog.controller.admin;

import com.blog.common.Result;
import com.blog.entity.Category;
import com.blog.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台分类管理控制器
 * 提供分类的增删改查接口（管理员权限）
 */
@RestController("adminCategoryController")
@RequestMapping("/api/admin/categories")
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

    /**
     * 新增分类
     */
    @PostMapping
    public Result<?> create(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return Result.success();
    }

    /**
     * 修改分类名称
     */
    @PutMapping
    public Result<?> update(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return Result.success();
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return Result.success();
    }
}
