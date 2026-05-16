package com.blog.service;

import com.blog.entity.SiteConfig;

/**
 * 站点配置服务接口
 */
public interface SiteConfigService {
    /** 获取站点配置（全局唯一） */
    SiteConfig get();
    /** 更新站点配置 */
    void update(SiteConfig siteConfig);
}
