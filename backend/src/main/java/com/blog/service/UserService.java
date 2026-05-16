package com.blog.service;

import com.blog.dto.LoginDTO;
import java.util.Map;

/**
 * 用户服务接口
 */
public interface UserService {
    /** 用户登录，验证凭据并返回Token */
    Map<String, Object> login(LoginDTO loginDTO);
    /** 修改管理员密码 */
    void updatePassword(Long userId, String oldPassword, String newPassword);
}
