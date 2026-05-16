package com.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.common.Result;
import com.blog.dto.ArticleQueryDTO;
import com.blog.dto.PageResult;
import com.blog.entity.Article;
import com.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 前台文章控制器
 * 提供公开访问的文章查询接口（无需登录）
 */
@RestController("frontArticleController")
@RequestMapping("/api/front/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 分页查询已发布文章列表
     * 强制只返回状态为"已发布"的文章
     */
    @GetMapping
    public Result<PageResult<Article>> list(ArticleQueryDTO query) {
        query.setStatus(1); // 前台只展示已发布文章
        IPage<Article> page = articleService.pageQuery(query);
        PageResult<Article> result = new PageResult<>();
        result.setRecords(page.getRecords());
        result.setTotal(page.getTotal());
        result.setPage(page.getCurrent());
        result.setSize(page.getSize());
        return Result.success(result);
    }

    /**
     * 查看文章详情（含上一篇/下一篇导航）
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        Article article = articleService.getById(id);
        // 获取相邻文章用于上下篇导航
        Article prev = articleService.getPrevArticle(id);
        Article next = articleService.getNextArticle(id);
        Map<String, Object> data = new HashMap<>();
        data.put("article", article);
        data.put("prev", prev != null ? Map.of("id", prev.getId(), "title", prev.getTitle()) : null);
        data.put("next", next != null ? Map.of("id", next.getId(), "title", next.getTitle()) : null);
        return Result.success(data);
    }
}
