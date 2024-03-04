package com.oxygen.shortlink.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.project.common.constants.RedisKeyConstant;
import com.oxygen.shortlink.project.dao.entity.ShortLinkDO;
import com.oxygen.shortlink.project.dao.mapper.ShortLinkMapper;
import com.oxygen.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.oxygen.shortlink.project.service.RecycleBinService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author LiJinLong
 * @description 回收站接口实现层
 * @create 2024-03-04 20:34
 * @date 1.0
 */
@Service
@AllArgsConstructor
public class RecycleBinServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements RecycleBinService {

    private StringRedisTemplate stringRedisTemplate;
    /**
     * 保存回收站
     *
     * @param requestParam
     */
    @Override
    public void saveRecycleBin(RecycleBinSaveReqDTO requestParam) {
        LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0);
        ShortLinkDO shortLinkDO = ShortLinkDO.builder().enableStatus(1).build();
        baseMapper.update(shortLinkDO, updateWrapper);
        // 删除缓存中的短链接
        stringRedisTemplate.delete(String.format(RedisKeyConstant.GOTO_SHORT_LINK_KEY, requestParam.getFullShortUrl()));
    }
}
