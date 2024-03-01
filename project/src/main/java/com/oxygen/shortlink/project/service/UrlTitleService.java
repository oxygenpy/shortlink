package com.oxygen.shortlink.project.service;

/**
 * @author LiJinLong
 * @description URL 标题接口层
 * @create 2024-03-01 21:29
 * @date 1.0
 */
public interface UrlTitleService {

    /**
     * 通过URL获取网站标题
     * @param url 网站地址
     * @return 网站标题
     */
    String getTitleByUrl(String url);

}
