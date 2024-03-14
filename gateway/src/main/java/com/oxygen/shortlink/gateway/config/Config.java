package com.oxygen.shortlink.gateway.config;

import lombok.Data;

import java.util.List;

/**
 * @author LiJinLong
 * @version 1.0
 * @description 过滤器配置
 * @create 2024-03-14 21:43
 */
@Data
public class Config {

    /**
     * 白名单前置路径
     */
    private List<String> whitePathList;
}
