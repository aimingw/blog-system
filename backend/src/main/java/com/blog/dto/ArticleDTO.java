package com.blog.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 文章数据传输对象
 * 用于接收前端提交的文章表单数据
 */
public class ArticleDTO {
    /** 文章ID（编辑时传入） */
    private Long id;
    /** 文章标题（必填） */
    @NotBlank(message = "Title is required")
    private String title;
    /** 文章正文内容 */
    private String content;
    /** 文章摘要 */
    private String summary;
    /** 所属分类ID */
    private Long categoryId;
    /** 文章状态：0草稿，1已发布 */
    private Integer status;
    /** 关联标签ID列表 */
    private List<Long> tagIds;

    public ArticleDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public List<Long> getTagIds() { return tagIds; }
    public void setTagIds(List<Long> tagIds) { this.tagIds = tagIds; }
}
