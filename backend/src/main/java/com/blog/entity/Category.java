package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 文章分类实体类
 * 对应数据库表 t_category
 */
@TableName("t_category")
public class Category {
    /** 主键ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 分类名称 */
    private String name;
    /** 创建时间 */
    private LocalDateTime createTime;

    public Category() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
