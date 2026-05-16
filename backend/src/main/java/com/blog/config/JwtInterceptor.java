package com.blog.config;

import com.blog.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT认证拦截器
 * 拦截管理员接口请求，校验请求头中的Bearer Token
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public JwtInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 请求预处理：校验JWT Token
     * @return true放行，false返回401未授权
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 放行预检请求（OPTIONS）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 从请求头提取Token并校验
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                Long userId = jwtUtils.getUserIdFromToken(token);
                // 将用户ID存入请求属性，供后续控制器使用
                request.setAttribute("userId", userId);
                return true;
            }
        }

        response.setStatus(401);
        return false;
    }
}
