package com.blog.controller.front;

import com.blog.common.Result;
import com.blog.entity.SiteConfig;
import com.blog.service.SiteConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("frontSiteConfigController")
@RequestMapping("/api/front/site-config")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    @GetMapping
    public Result<SiteConfig> get() {
        return Result.success(siteConfigService.get());
    }
}
