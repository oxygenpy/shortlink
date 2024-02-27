package com.oxygen.shortlink.admin.test;

/**
 * @author LiJinLong
 * @description
 * @create 2024-01-25 20:31
 * @date 1.0
 */

public class ShortLinkTest {
    public static final String SQL = "CREATE TABLE `t_link_goto_%d` (\n" +
            "`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "`gid` VARCHAR(32) DEFAULT 'default' COMMENT '分组标识',\n" +
            "`full_short_url` VARCHAR(128) DEFAULT NULL COMMENT '完整短链接',\n" +
            "PRIMARY KEY (`id`)\n" +
            ") ENGINE=INNODB DEFAULT CHARSET=utf8mb4;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}
