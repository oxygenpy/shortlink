package com.oxygen.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author LiJinLong
 * @description
 * @create 2024-01-19 21:48
 * @date 1.0
 */
@EnableFeignClients("com.oxygen.shortlink.admin.remote")
@MapperScan("com.oxygen.shortlink.admin.dao.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class ShortLinkAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkAdminApplication.class, args);
    }

}
