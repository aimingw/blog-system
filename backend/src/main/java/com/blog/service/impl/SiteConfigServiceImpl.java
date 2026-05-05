package com.blog.service.impl;

import com.blog.entity.SiteConfig;
import com.blog.mapper.SiteConfigMapper;
import com.blog.service.SiteConfigService;
import org.springframework.stereotype.Service;

@Service
public class SiteConfigServiceImpl implements SiteConfigService {

    private final SiteConfigMapper siteConfigMapper;

    public SiteConfigServiceImpl(SiteConfigMapper siteConfigMapper) {
        this.siteConfigMapper = siteConfigMapper;
    }

    @Override
    public SiteConfig get() {
        return siteConfigMapper.selectById(1L);
    }

    @Override
    public void update(SiteConfig siteConfig) {
        siteConfig.setId(1L);
        siteConfigMapper.updateById(siteConfig);
    }
}
