package com.blog.dto;

public class ArticleQueryDTO {
    private Integer page = 1;
    private Integer size = 10;
    private String keyword;
    private Long categoryId;
    private Long tagId;
    private Integer status;

    public ArticleQueryDTO() {}

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
