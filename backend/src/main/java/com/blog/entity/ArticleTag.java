package com.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_article_tag")
public class ArticleTag {
    private Long articleId;
    private Long tagId;

    public ArticleTag() {}

    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }
}
