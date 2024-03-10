package com.oxygen.shortlink.admin.test;

/**
 * @author LiJinLong
 * @description
 * @create 2024-01-25 20:31
 * @date 1.0
 */

public class ShortLinkTest {
    public static final String SQL1 = "DROP TABLE IF EXISTS `t_link_stats_today_%d`;\n";
    public static final String SQL2 = "CREATE TABLE `t_link_stats_today_%d`  (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default' COMMENT '分组标识',\n" +
            "  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短链接',\n" +
            "  `date` date NULL DEFAULT NULL COMMENT '日期',\n" +
            "  `today_pv` int NULL DEFAULT 0 COMMENT '今日PV',\n" +
            "  `today_uv` int NULL DEFAULT 0 COMMENT '今日UV',\n" +
            "  `today_uip` int NULL DEFAULT 0 COMMENT '今日IP数',\n" +
            "  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',\n" +
            "  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  UNIQUE INDEX `idx_unique_today_stats`(`full_short_url` ASC, `gid` ASC, `date` ASC) USING BTREE\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;\n" +
            "\n" +
            "SET FOREIGN_KEY_CHECKS = 1;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL1) + "%n", i);
            System.out.printf((SQL2) + "%n", i);
        }
    }
}
