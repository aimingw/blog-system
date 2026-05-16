-- =============================================
-- 博客系统数据库初始化脚本
-- 适用于 MySQL 8.0+
-- =============================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS blog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blog;

-- ====== 用户表 ======
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(64) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
  `nickname` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员用户表';

-- 插入默认管理员账号：admin / admin123
INSERT INTO `t_user` (`username`, `password`, `nickname`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'Admin');

-- ====== 分类表 ======
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(64) NOT NULL COMMENT '分类名称',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章分类表';

-- 插入默认分类
INSERT INTO `t_category` (`name`) VALUES ('Technology'), ('Life'), ('Notes');

-- ====== 标签表 ======
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(64) NOT NULL COMMENT '标签名称',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 插入默认标签
INSERT INTO `t_tag` (`name`) VALUES ('Java'), ('Spring Boot'), ('Vue'), ('MySQL'), ('Docker');

-- ====== 文章表 ======
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(255) NOT NULL COMMENT '文章标题',
  `content` LONGTEXT COMMENT '文章正文（Markdown格式）',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '文章摘要',
  `category_id` BIGINT DEFAULT NULL COMMENT '所属分类ID',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0=草稿，1=已发布',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- ====== 文章-标签关联表（多对多） ======
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag` (
  `article_id` BIGINT NOT NULL COMMENT '文章ID',
  `tag_id` BIGINT NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`article_id`, `tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章标签关联表';

-- ====== 评论表 ======
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` BIGINT NOT NULL COMMENT '所属文章ID',
  `nick_name` VARCHAR(64) NOT NULL COMMENT '评论者昵称',
  `email` VARCHAR(128) NOT NULL COMMENT '评论者邮箱',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态：0=待审核，1=已通过，2=已拒绝',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ====== 站点配置表 ======
DROP TABLE IF EXISTS `t_site_config`;
CREATE TABLE `t_site_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(255) DEFAULT 'My Blog' COMMENT '站点标题',
  `subtitle` VARCHAR(255) DEFAULT '' COMMENT '副标题',
  `announcement` VARCHAR(500) DEFAULT '' COMMENT '站点公告',
  `about_content` LONGTEXT COMMENT '"关于"页面内容（Markdown格式）',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='站点配置表';

-- 插入默认站点配置
INSERT INTO `t_site_config` (`title`, `subtitle`, `announcement`, `about_content`) VALUES
('My Blog', 'A personal blog about technology and life', 'Welcome to my blog!', '## About Me\n\nThis is a personal blog built with Spring Boot and Vue 3.\n\nFeel free to leave comments.');
