package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.dto.ArticleDTO;
import com.blog.dto.ArticleQueryDTO;
import com.blog.entity.Article;

/**
 * 文章服务接口
 * 定义文章相关的业务操作方法
 */
public interface ArticleService {
    /** 分页查询文章 */
    IPage<Article> pageQuery(ArticleQueryDTO query);
    /** 根据ID获取文章 */
    Article getById(Long id);
    /** 获取上一篇文章（用于导航） */
    Article getPrevArticle(Long id);
    /** 获取下一篇文章（用于导航） */
    Article getNextArticle(Long id);
    /** 保存或更新文章（含标签关联处理） */
    void saveOrUpdate(ArticleDTO articleDTO);
    /** 删除文章及其关联的标签关系 */
    void deleteById(Long id);
}
