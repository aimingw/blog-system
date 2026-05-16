package com.blog.controller.admin;

import com.blog.common.Result;
import com.blog.entity.SiteConfig;
import com.blog.service.SiteConfigService;
import org.springframework.web.bind.annotation.*;

/**
 * 后台站点配置控制器
 * 提供站点全局设置的查询和修改接口（管理员权限）
 */
@RestController("adminSiteConfigController")
@RequestMapping("/api/admin/site-config")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    /**
     * 获取当前站点配置
     */
    @GetMapping
    public Result<SiteConfig> get() {
        return Result.success(siteConfigService.get());
    }

    /**
     * 更新站点配置（标题、副标题、公告、关于内容等）
     */
    @PutMapping
    public Result<?> update(@RequestBody SiteConfig siteConfig) {
        siteConfigService.update(siteConfig);
        return Result.success();
    }
}
