package com.oxygen.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * @author LiJinLong
 * @description 回收站接口层
 * @create 2024-03-04 22:19
 * @date 1.0
 */
public interface RecycleBinService {

    /**
     * 分页查询回收站短链接
     * @param requestParam 分页参数
     * @return 查询数据
     */
    Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);
}
