package com.oxygen.shortlink.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author LiJinLong
 * @description 短连接应用
 * @create 2024-01-19 21:48
 * @date 1.0
 */
@MapperScan("com.oxygen.shortlink.project.dao.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class ShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class, args);
    }

}
