package com.blog.controller.admin;

import com.blog.common.Result;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台标签管理控制器
 * 提供标签的增删改查接口（管理员权限）
 */
@RestController("adminTagController")
@RequestMapping("/api/admin/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 获取全部标签列表
     */
    @GetMapping
    public Result<List<Tag>> list() {
        return Result.success(tagService.listAll());
    }

    /**
     * 新增标签
     */
    @PostMapping
    public Result<?> create(@RequestBody Tag tag) {
        tagService.saveOrUpdate(tag);
        return Result.success();
    }

    /**
     * 修改标签名称
     */
    @PutMapping
    public Result<?> update(@RequestBody Tag tag) {
        tagService.saveOrUpdate(tag);
        return Result.success();
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        tagService.deleteById(id);
        return Result.success();
    }
}
