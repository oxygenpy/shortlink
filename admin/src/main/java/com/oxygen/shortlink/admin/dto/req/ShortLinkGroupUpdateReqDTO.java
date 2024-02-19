package com.oxygen.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 修改短链接分组名称参数
 * @create 2024-02-18 16:03
 * @date 1.0
 */
@Data
public class ShortLinkGroupUpdateReqDTO {
    /**
     * 短连接分组名称
     */
    private String name;

    /**
     * 分组标识
     */
    private String gid;

}
