package com.blog.controller.admin;

import com.blog.common.Result;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminTagController")
@RequestMapping("/api/admin/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public Result<List<Tag>> list() {
        return Result.success(tagService.listAll());
    }

    @PostMapping
    public Result<?> create(@RequestBody Tag tag) {
        tagService.saveOrUpdate(tag);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Tag tag) {
        tagService.saveOrUpdate(tag);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        tagService.deleteById(id);
        return Result.success();
    }
}
