package com.oxygen.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 查询短连接返回参数
 * @create 2024-02-18 16:52
 * @date 1.0
 */
@Data
public class ShortLinkGroupRespDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组排序
     */
    private Integer sortOrder;

    /**
     * 当前分组下短链接数量
     */
    private Integer shortLinkCount;
}
