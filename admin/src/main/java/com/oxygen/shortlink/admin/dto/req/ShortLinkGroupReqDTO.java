package com.oxygen.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 新增短连接请求参数
 * @create 2024-02-18 16:03
 * @date 1.0
 */
@Data
public class ShortLinkGroupReqDTO {
    /**
     * 短连接分组名称
     */
    private String name;
}
