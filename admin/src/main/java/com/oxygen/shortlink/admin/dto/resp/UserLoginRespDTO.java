package com.oxygen.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiJinLong
 * @description 登录返回类型
 * @create 2024-01-27 17:04
 * @date 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRespDTO {

    /**
     * 登录token
     */
    private String token;
}
