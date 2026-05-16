package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.BusinessException;
import com.blog.dto.LoginDTO;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import com.blog.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 * 负责登录认证和密码管理
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserMapper userMapper, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 管理员登录
     * 校验用户名和密码，成功后返回JWT Token和用户信息
     */
    @Override
    public Map<String, Object> login(LoginDTO loginDTO) {
        // 查询用户
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, loginDTO.getUsername()));
        // 校验密码（BCrypt比对）
        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "Invalid username or password");
        }
        // 生成JWT Token
        String token = jwtUtils.generateToken(user.getId(), user.getUsername());
        // 返回前清除密码字段
        user.setPassword(null);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    /**
     * 修改管理员密码
     * 需先验证旧密码正确性
     */
    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateById(user);
    }
}
