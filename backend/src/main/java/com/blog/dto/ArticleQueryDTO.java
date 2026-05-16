package com.blog.dto;

/**
 * 文章查询参数DTO
 * 用于接收前端传递的文章列表筛选和分页参数
 */
public class ArticleQueryDTO {
    /** 当前页码（默认1） */
    private Integer page = 1;
    /** 每页条数（默认10） */
    private Integer size = 10;
    /** 搜索关键词 */
    private String keyword;
    /** 按分类ID筛选 */
    private Long categoryId;
    /** 按标签ID筛选 */
    private Long tagId;
    /** 按状态筛选：0草稿，1已发布 */
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
