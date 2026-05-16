package com.blog.controller.front;

import com.blog.common.Result;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台标签控制器
 * 提供公开访问的标签列表接口（无需登录）
 */
@RestController("frontTagController")
@RequestMapping("/api/front/tags")
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
}
