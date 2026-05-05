package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.dto.ArticleDTO;
import com.blog.dto.ArticleQueryDTO;
import com.blog.entity.Article;

public interface ArticleService {
    IPage<Article> pageQuery(ArticleQueryDTO query);
    Article getById(Long id);
    Article getPrevArticle(Long id);
    Article getNextArticle(Long id);
    void saveOrUpdate(ArticleDTO articleDTO);
    void deleteById(Long id);
}
