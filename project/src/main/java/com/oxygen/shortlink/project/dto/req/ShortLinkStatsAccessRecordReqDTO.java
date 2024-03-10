package com.oxygen.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oxygen.shortlink.project.dao.entity.LinkAccessLogsDO;
import lombok.Data;

/**
 * @author LiJinLong
 * @description 单个短链接指定时间内的访客记录请求参数
 * @create 2024-03-09 21:02
 * @date 1.0
 */
@Data
public class ShortLinkStatsAccessRecordReqDTO extends Page<LinkAccessLogsDO> {

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