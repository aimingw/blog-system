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

@RestController("frontArticleController")
@RequestMapping("/api/front/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public Result<PageResult<Article>> list(ArticleQueryDTO query) {
        query.setStatus(1);
        IPage<Article> page = articleService.pageQuery(query);
        PageResult<Article> result = new PageResult<>();
        result.setRecords(page.getRecords());
        result.setTotal(page.getTotal());
        result.setPage(page.getCurrent());
        result.setSize(page.getSize());
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        Article article = articleService.getById(id);
        Article prev = articleService.getPrevArticle(id);
        Article next = articleService.getNextArticle(id);
        Map<String, Object> data = new HashMap<>();
        data.put("article", article);
        data.put("prev", prev != null ? Map.of("id", prev.getId(), "title", prev.getTitle()) : null);
        data.put("next", next != null ? Map.of("id", next.getId(), "title", next.getTitle()) : null);
        return Result.success(data);
    }
}
