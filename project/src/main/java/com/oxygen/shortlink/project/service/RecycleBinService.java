package com.oxygen.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oxygen.shortlink.project.dao.entity.ShortLinkDO;
import com.oxygen.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * @author LiJinLong
 * @description 回收站接口层
 * @create 2024-03-04 20:34
 * @date 1.0
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    /**
     * 回收站分页查询
     * @param requestParam
     * @return
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParam);
}
