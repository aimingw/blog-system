package com.blog.service.impl;

import com.blog.entity.SiteConfig;
import com.blog.mapper.SiteConfigMapper;
import com.blog.service.SiteConfigService;
import org.springframework.stereotype.Service;

/**
 * 站点配置服务实现类
 * 站点配置全局唯一，固定使用ID=1的记录
 */
@Service
public class SiteConfigServiceImpl implements SiteConfigService {

    private final SiteConfigMapper siteConfigMapper;

    public SiteConfigServiceImpl(SiteConfigMapper siteConfigMapper) {
        this.siteConfigMapper = siteConfigMapper;
    }

    /**
     * 获取站点配置（固定读取ID=1的记录）
     */
    @Override
    public SiteConfig get() {
        return siteConfigMapper.selectById(1L);
    }

    /**
     * 更新站点配置（强制更新ID=1的记录）
     */
    @Override
    public void update(SiteConfig siteConfig) {
        siteConfig.setId(1L);
        siteConfigMapper.updateById(siteConfig);
    }
}
