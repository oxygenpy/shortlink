package com.oxygen.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.project.dao.entity.ShortLinkDO;
import com.oxygen.shortlink.project.dao.mapper.ShortLinkMapper;
import com.oxygen.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.oxygen.shortlink.project.service.ShortLinkService;
import com.oxygen.shortlink.project.toolkit.HashUtil;
import org.springframework.stereotype.Service;

/**
 * @author LiJinLong
 * @description 短链接接口实现层
 * @create 2024-02-19 17:14
 * @date 1.0
 */
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    /**
     * 创建短链接
     *
     * @param requestParam
     * @return
     */
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam) {
        String suffix = generatorSuffix(requestParam);
        ShortLinkDO shortLink = BeanUtil.copyProperties(requestParam, ShortLinkDO.class);
        shortLink.setShortUri(suffix);
        shortLink.setFullShortUrl(shortLink.getDomain() + "/" + suffix);
        baseMapper.insert(shortLink);
        return ShortLinkCreateRespDTO.builder()
                .gid(shortLink.getGid())
                .originUrl(shortLink.getOriginUrl())
                .fullShortUrl(shortLink.getFullShortUrl())
                .build();
    }


    String generatorSuffix(ShortLinkCreateReqDTO requestParam) {
        return HashUtil.hashToBase62(requestParam.getOriginUrl());
    }
}
