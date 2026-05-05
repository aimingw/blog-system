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

@RestController("adminArticleController")
@RequestMapping("/api/admin/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleTagMapper articleTagMapper;

    public ArticleController(ArticleService articleService, ArticleTagMapper articleTagMapper) {
        this.articleService = articleService;
        this.articleTagMapper = articleTagMapper;
    }

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

    @GetMapping("/{id}")
    public Result<Map<String, Object>> getById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        List<ArticleTag> ats = articleTagMapper.selectList(
                new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, id));
        List<Long> tagIds = ats.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("article", article);
        data.put("tagIds", tagIds);
        return Result.success(data);
    }

    @PostMapping
    public Result<?> create(@RequestBody ArticleDTO articleDTO) {
        articleDTO.setId(null);
        articleService.saveOrUpdate(articleDTO);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ArticleDTO articleDTO) {
        articleService.saveOrUpdate(articleDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        articleService.deleteById(id);
        return Result.success();
    }
}
