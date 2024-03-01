package com.oxygen.shortlink.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LiJinLong
 * @description 短链接不存在跳转控制器
 * @create 2024-03-01 20:55
 * @date 1.0
 */
@Controller
public class ShortLinkNotfoundController {

    /**
     * 短链接不存在跳转页面
     */
    @RequestMapping("/page/notfound")
    public String notfound() {
        return "notfound";
    }
}
