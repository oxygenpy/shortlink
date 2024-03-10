package com.oxygen.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * @author LiJinLong
 * @description 短链接监控接口层
 * @create 2024-03-07 21:03
 * @date 1.0
 */
public interface ShortLinkStatsService {

    /**
     * 访问当个短链接指定时间内的监控数据
     * @param requestParam gid url date
     * @return 监控数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);

    /**
     * 单个短链接指定时间内的访客记录
     * @param requestParam
     * @return
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam);
}
