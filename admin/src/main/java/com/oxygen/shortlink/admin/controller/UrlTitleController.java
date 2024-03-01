package com.oxygen.shortlink.admin.controller;

import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.remote.ShortLinKRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJinLong
 * @description URL 标题控制层
 * @create 2024-03-01 21:38
 * @date 1.0
 */
@RestController
public class UrlTitleController {

    ShortLinKRemoteService shortLinKRemoteService = new ShortLinKRemoteService(){};

    /**
     * 通过 url 获取 网站标题
     */
    @GetMapping("/api/short-link/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return shortLinKRemoteService.getTitleByUrl(url);
    }


}
