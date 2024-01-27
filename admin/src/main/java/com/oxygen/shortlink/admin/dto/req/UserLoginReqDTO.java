package com.oxygen.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 用户登录请求参数类
 * @create 2024-01-27 17:03
 * @date 1.0
 */
@Data
public class UserLoginReqDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
