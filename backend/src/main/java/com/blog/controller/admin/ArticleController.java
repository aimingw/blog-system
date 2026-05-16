package com.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.common.Result;
import com.blog.dto.ArticleDTO;
import com.blog.dto.ArticleQueryDTO;
import com.blog.dto.PageResult;
import com.blog.entity.Article;
import com.blog.entity.ArticleTag;
import com.blog.mapper.ArticleTagMapper;
import com.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台文章管理控制器
 * 提供文章的增删改查接口（管理员权限）
 */
@RestController("adminArticleController")
@RequestMapping("/api/admin/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleTagMapper articleTagMapper;

    public ArticleController(ArticleService articleService, ArticleTagMapper articleTagMapper) {
        this.articleService = articleService;
        this.articleTagMapper = articleTagMapper;
    }

    /**
     * 分页查询文章列表（支持关键词、分类、标签、状态筛选）
     */
    @GetMapping
    public Result<PageResult<Article>> list(ArticleQueryDTO query) {
        IPage<Article> page = articleService.pageQuery(query);
        PageResult<Article> result = new PageResult<>();
        result.setRecords(page.getRecords());
        result.setTotal(page.getTotal());
        result.setPage(page.getCurrent());
        result.setSize(page.getSize());
        return Result.success(result);
    }

    /**
     * 根据ID获取文章详情（包含关联的标签ID列表）
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> getById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        // 查询文章关联的所有标签
        List<ArticleTag> ats = articleTagMapper.selectList(
                new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, id));
        List<Long> tagIds = ats.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("article", article);
        data.put("tagIds", tagIds);
        return Result.success(data);
    }

    /**
     * 创建新文章
     */
    @PostMapping
    public Result<?> create(@RequestBody ArticleDTO articleDTO) {
        articleDTO.setId(null);
        articleService.saveOrUpdate(articleDTO);
        return Result.success();
    }

    /**
     * 更新已有文章
     */
    @PutMapping
    public Result<?> update(@RequestBody ArticleDTO articleDTO) {
        articleService.saveOrUpdate(articleDTO);
        return Result.success();
    }

    /**
     * 删除文章（同时删除关联的标签关系）
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        articleService.deleteById(id);
        return Result.success();
    }
}
