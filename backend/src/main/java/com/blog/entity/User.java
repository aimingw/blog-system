package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表 t_user，存储管理员账号信息
 */
@TableName("t_user")
public class User {
    /** 主键ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 用户名（登录用） */
    private String username;
    /** 密码（BCrypt加密存储） */
    private String password;
    /** 用户昵称 */
    private String nickname;
    /** 头像URL */
    private String avatar;
    /** 创建时间 */
    private LocalDateTime createTime;

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
