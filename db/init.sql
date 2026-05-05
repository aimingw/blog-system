-- Blog System Database Initialization
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS blog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blog;

-- User table
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `username` VARCHAR(64) NOT NULL COMMENT 'Username',
  `password` VARCHAR(255) NOT NULL COMMENT 'Password (BCrypt)',
  `nickname` VARCHAR(64) DEFAULT NULL COMMENT 'Nickname',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT 'Avatar URL',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created Time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Admin User Table';

-- Insert default admin: admin / admin123
INSERT INTO `t_user` (`username`, `password`, `nickname`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'Admin');

-- Category table
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `name` VARCHAR(64) NOT NULL COMMENT 'Category Name',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created Time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Category Table';

INSERT INTO `t_category` (`name`) VALUES ('Technology'), ('Life'), ('Notes');

-- Tag table
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `name` VARCHAR(64) NOT NULL COMMENT 'Tag Name',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created Time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tag Table';

INSERT INTO `t_tag` (`name`) VALUES ('Java'), ('Spring Boot'), ('Vue'), ('MySQL'), ('Docker');

-- Article table
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `title` VARCHAR(255) NOT NULL COMMENT 'Title',
  `content` LONGTEXT COMMENT 'Content (Markdown)',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT 'Summary',
  `category_id` BIGINT DEFAULT NULL COMMENT 'Category ID',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT 'Status: 0=draft, 1=published',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created Time',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated Time',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Article Table';

-- Article-Tag association table
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag` (
  `article_id` BIGINT NOT NULL COMMENT 'Article ID',
  `tag_id` BIGINT NOT NULL COMMENT 'Tag ID',
  PRIMARY KEY (`article_id`, `tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Article Tag Association Table';

-- Comment table
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `article_id` BIGINT NOT NULL COMMENT 'Article ID',
  `nick_name` VARCHAR(64) NOT NULL COMMENT 'Nickname',
  `email` VARCHAR(128) NOT NULL COMMENT 'Email',
  `content` TEXT NOT NULL COMMENT 'Comment Content',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT 'Status: 0=pending, 1=approved, 2=rejected',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created Time',
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Comment Table';

-- Site configuration table
DROP TABLE IF EXISTS `t_site_config`;
CREATE TABLE `t_site_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `title` VARCHAR(255) DEFAULT 'My Blog' COMMENT 'Site Title',
  `subtitle` VARCHAR(255) DEFAULT '' COMMENT 'Subtitle',
  `announcement` VARCHAR(500) DEFAULT '' COMMENT 'Announcement',
  `about_content` LONGTEXT COMMENT 'About Page Content (Markdown)',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated Time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Site Configuration Table';

INSERT INTO `t_site_config` (`title`, `subtitle`, `announcement`, `about_content`) VALUES
('My Blog', 'A personal blog about technology and life', 'Welcome to my blog!', '## About Me\n\nThis is a personal blog built with Spring Boot and Vue 3.\n\nFeel free to leave comments.');
