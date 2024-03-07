package com.oxygen.shortlink.project.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 单个短链接访问监控请求
 * @create 2024-03-07 21:05
 * @date 1.0
 */
@Data
public class ShortLinkStatsReqDTO {

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;


}
