package com.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 文章-标签关联实体类
 * 对应数据库表 t_article_tag（多对多中间表）
 */
@TableName("t_article_tag")
public class ArticleTag {
    /** 文章ID */
    private Long articleId;
    /** 标签ID */
    private Long tagId;

    public ArticleTag() {}

    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }
}
