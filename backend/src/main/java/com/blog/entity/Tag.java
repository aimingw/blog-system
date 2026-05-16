package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 标签实体类
 * 对应数据库表 t_tag
 */
@TableName("t_tag")
public class Tag {
    /** 主键ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 标签名称 */
    private String name;
    /** 创建时间 */
    private LocalDateTime createTime;

    public Tag() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
