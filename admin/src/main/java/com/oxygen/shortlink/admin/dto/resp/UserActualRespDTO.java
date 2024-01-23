package com.oxygen.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 用户返回无脱敏参数响应
 * @create 2024-01-20 21:52
 * @date 1.0
 */
@Data
public class UserActualRespDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
