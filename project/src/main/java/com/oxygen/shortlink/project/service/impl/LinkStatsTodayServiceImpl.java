package com.oxygen.shortlink.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.project.dao.entity.LinkStatsTodayDO;
import com.oxygen.shortlink.project.dao.mapper.LinkStatsTodayMapper;
import com.oxygen.shortlink.project.service.LinkStatsTodayService;
import org.springframework.stereotype.Service;

/**
 * @author LiJinLong
 * @description 短链接今日统计接口实现层
 * @create 2024-03-11 22:39
 * @date 1.0
 */
@Service
public class LinkStatsTodayServiceImpl extends ServiceImpl<LinkStatsTodayMapper, LinkStatsTodayDO> implements LinkStatsTodayService {
}