package com.blog.controller.admin;

import com.blog.common.Result;
import com.blog.entity.SiteConfig;
import com.blog.service.SiteConfigService;
import org.springframework.web.bind.annotation.*;

@RestController("adminSiteConfigController")
@RequestMapping("/api/admin/site-config")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    @GetMapping
    public Result<SiteConfig> get() {
        return Result.success(siteConfigService.get());
    }

    @PutMapping
    public Result<?> update(@RequestBody SiteConfig siteConfig) {
        siteConfigService.update(siteConfig);
        return Result.success();
    }
}
