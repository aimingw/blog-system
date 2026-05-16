package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 评论实体类
 * 对应数据库表 t_comment
 */
@TableName("t_comment")
public class Comment {
    /** 主键ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 评论所属文章ID */
    private Long articleId;
    /** 评论者昵称 */
    private String nickName;
    /** 评论者邮箱 */
    private String email;
    /** 评论内容 */
    private String content;
    /** 审核状态：0待审核，1已通过，2已拒绝 */
    private Integer status;
    /** 创建时间 */
    private LocalDateTime createTime;

    public Comment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
