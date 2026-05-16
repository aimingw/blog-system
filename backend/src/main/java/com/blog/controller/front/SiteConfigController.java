package com.blog.controller.front;

import com.blog.common.Result;
import com.blog.entity.SiteConfig;
import com.blog.service.SiteConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台站点配置控制器
 * 提供公开的站点信息查询接口（标题、公告、关于内容等）
 */
@RestController("frontSiteConfigController")
@RequestMapping("/api/front/site-config")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    /**
     * 获取站点全局配置
     */
    @GetMapping
    public Result<SiteConfig> get() {
        return Result.success(siteConfigService.get());
    }
}
