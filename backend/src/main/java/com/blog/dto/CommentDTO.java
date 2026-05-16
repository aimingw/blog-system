package com.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 评论数据传输对象
 * 用于接收前端提交的评论表单数据，带参数校验
 */
public class CommentDTO {
    /** 评论所属文章ID */
    private Long articleId;
    /** 评论者昵称（必填） */
    @NotBlank(message = "Nickname is required")
    private String nickName;
    /** 评论者邮箱（必填，需符合邮箱格式） */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    /** 评论内容（必填） */
    @NotBlank(message = "Content is required")
    private String content;

    public CommentDTO() {}

    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
