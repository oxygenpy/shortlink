package com.oxygen.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 查询单个分组的监控数据
 * @create 2024-03-10 22:45
 * @date 1.0
 */
@Data
public class ShortLinkGroupStatsReqDTO {

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