package com.blog.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求DTO
 * 接收管理员登录表单数据
 */
public class LoginDTO {
    /** 用户名（必填） */
    @NotBlank(message = "Username is required")
    private String username;
    /** 密码（必填） */
    @NotBlank(message = "Password is required")
    private String password;

    public LoginDTO() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
