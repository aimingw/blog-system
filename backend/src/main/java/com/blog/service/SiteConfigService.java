package com.blog.service;

import com.blog.entity.SiteConfig;

public interface SiteConfigService {
    SiteConfig get();
    void update(SiteConfig siteConfig);
}
