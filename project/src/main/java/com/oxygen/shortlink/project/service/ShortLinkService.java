package com.oxygen.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oxygen.shortlink.project.dao.entity.ShortLinkDO;
import com.oxygen.shortlink.project.dto.biz.ShortLinkStatsRecordDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkBatchCreateReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.oxygen.shortlink.project.dto.resp.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.util.List;

/**
 * @author LiJinLong
 * @description 短链接接口层
 * @create 2024-02-19 17:13
 * @date 1.0
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     * @param requestParam
     * @return
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);

    /**
     * 查询分组下短链接数量
     * @param requestParam
     * @return
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam);

    /**
     * 修改短链接
     * @param requestParam
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);

    /**
     * 短链接跳转原始链接
     * @param shortUri 短链接后缀
     * @param request HTTP 请求
     * @param response HTTP 响应
     */
    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response);


    /**
     * 批量创建短链接
     *
     * @param requestParam 批量创建短链接请求参数
     * @return 批量创建短链接返回参数
     */
    ShortLinkBatchCreateRespDTO batchCreateShortLink(ShortLinkBatchCreateReqDTO requestParam);

    /**
     * 短链接统计
     *
     * @param fullShortUrl         完整短链接
     * @param gid                  分组标识
     * @param shortLinkStatsRecord 短链接统计实体参数
     */
    void shortLinkStats(String fullShortUrl, String gid, ShortLinkStatsRecordDTO shortLinkStatsRecord);
}
