package com.blog.service;

import com.blog.dto.LoginDTO;
import java.util.Map;

public interface UserService {
    Map<String, Object> login(LoginDTO loginDTO);
    void updatePassword(Long userId, String oldPassword, String newPassword);
}
