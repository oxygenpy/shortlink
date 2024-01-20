package com.oxygen.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LiJinLong
 * @description
 * @create 2024-01-19 21:48
 * @date 1.0
 */
@MapperScan("com.oxygen.shortlink.admin.dao.mapper")
@SpringBootApplication
public class ShortLinkAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkAdminApplication.class, args);
    }

}
