package com.blog.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class ArticleDTO {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private Integer status;
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
