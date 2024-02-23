package com.oxygen.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 分组下短链接数量
 * @create 2024-02-22 16:50
 * @date 1.0
 */
@Data
public class ShortLinkGroupCountRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组下短链接数量
     */
    private Integer shortLinkCount;

}
