package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 博客系统主启动类
 */
@SpringBootApplication
@MapperScan("com.blog.mapper")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    /**
     * 启动时生成管理员默认密码的BCrypt哈希值（用于初始化数据库）
     */
    @Bean
    CommandLineRunner initPassword() {
        return args -> {
            System.out.println("==============================================");
            System.out.println("BCrypt hash for 'admin123': " + new BCryptPasswordEncoder().encode("admin123"));
            System.out.println("==============================================");
        };
    }
}
