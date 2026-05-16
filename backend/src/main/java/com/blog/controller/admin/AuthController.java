package com.blog.controller.admin;

import com.blog.common.Result;
import com.blog.dto.LoginDTO;
import com.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员认证控制器
 * 处理管理员登录、登出等认证相关接口
 */
@RestController
@RequestMapping("/api/admin")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 管理员登录
     * 验证用户名密码，返回JWT Token
     */
    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return Result.success(userService.login(loginDTO));
    }
}
