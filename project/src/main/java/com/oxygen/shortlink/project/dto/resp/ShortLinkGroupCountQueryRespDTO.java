package com.oxygen.shortlink.project.dto.resp;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 短链接分组查询返回参数
 * @create 2024-03-11 21:58
 * @date 1.0
 */
@Data
public class ShortLinkGroupCountQueryRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 短链接数量
     */
    private Integer shortLinkCount;
}