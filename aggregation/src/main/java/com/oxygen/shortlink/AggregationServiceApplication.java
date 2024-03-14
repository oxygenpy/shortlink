package com.oxygen.shortlink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author LiJinLong
 * @version 1.0
 * @description 短链接聚合应用
 * @create 2024-03-14 22:10
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.oxygen.shortlink.admin",
        "com.oxygen.shortlink.project"
})
@MapperScan(value = {
        "com.oxygen.shortlink.project.dao.mapper",
        "com.oxygen.shortlink.admin.dao.mapper"
})
public class AggregationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}