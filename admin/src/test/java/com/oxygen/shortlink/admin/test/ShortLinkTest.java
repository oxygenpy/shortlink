package com.oxygen.shortlink.admin.test;

/**
 * @author LiJinLong
 * @description
 * @create 2024-01-25 20:31
 * @date 1.0
 */

public class ShortLinkTest {
    public static final String SQL = "CREATE TABLE `t_group_%d`  (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组标识',\n" +
            "  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组名称',\n" +
            "  `username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建分组用户名',\n" +
            "  `sort_order` int NULL DEFAULT NULL COMMENT '分组排序',\n" +
            "  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',\n" +
            "  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  UNIQUE INDEX `idx_unique_gid_username`(`gid` ASC, `username` ASC) USING BTREE COMMENT '联合索引'\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 1759194263993774082 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短链接分组' ROW_FORMAT = DYNAMIC;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}
