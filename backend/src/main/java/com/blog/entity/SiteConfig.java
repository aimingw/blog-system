package com.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 站点配置实体类
 * 对应数据库表 t_site_config，存储博客站点全局设置
 */
@TableName("t_site_config")
public class SiteConfig {
    /** 主键ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 站点标题 */
    private String title;
    /** 站点副标题 */
    private String subtitle;
    /** 站点公告 */
    private String announcement;
    /** "关于"页面内容 */
    private String aboutContent;
    /** 最后更新时间 */
    private LocalDateTime updateTime;

    public SiteConfig() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public String getAnnouncement() { return announcement; }
    public void setAnnouncement(String announcement) { this.announcement = announcement; }
    public String getAboutContent() { return aboutContent; }
    public void setAboutContent(String aboutContent) { this.aboutContent = aboutContent; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}
