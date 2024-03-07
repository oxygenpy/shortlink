package com.oxygen.shortlink.project.service.impl;

import com.oxygen.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkStatsRespDTO;
import com.oxygen.shortlink.project.service.ShortLinkStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author LiJinLong
 * @description 短链接监控接口实现层
 * @create 2024-03-07 21:03
 * @date 1.0
 */
@Service
@RequiredArgsConstructor
public class ShortLinkStatsServiceImpl implements ShortLinkStatsService {


    /**
     * 访问当个短链接指定时间内的监控数据
     *
     * @param requestParam gid url date
     * @return 监控数据
     */
    @Override
    public ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam) {


        return null;
    }
}
