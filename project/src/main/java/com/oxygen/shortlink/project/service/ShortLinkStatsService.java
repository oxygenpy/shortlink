package com.oxygen.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.project.dto.req.ShortLinkGroupStatsAccessRecordReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkGroupStatsReqDTO;
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

    /**
     * 获取分组短链接监控数据
     *
     * @param requestParam 获取分组短链接监控数据入参
     * @return 分组短链接监控数据
     */
    ShortLinkStatsRespDTO groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam);

    /**
     * 访问分组短链接指定时间内访问记录监控数据
     *
     * @param requestParam 获取分组短链接监控访问记录数据入参
     * @return 分组访问记录监控数据
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam);
}
