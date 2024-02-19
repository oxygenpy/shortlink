package com.oxygen.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 短链接分组排序参数
 * @create 2024-02-18 16:03
 * @date 1.0
 */
@Data
public class ShortLinkGroupSortReqDTO {
    /**
     * 短连接分组名称
     */
    private String gid;

    /**
     * 分组排序
     */
    private Integer sortOrder;

}
