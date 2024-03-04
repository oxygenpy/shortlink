package com.oxygen.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.remote.ShortLinkRemoteService;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJinLong
 * @description 短链接后管控制层
 * @create 2024-02-22 15:06
 * @date 1.0
 */
@RestController
public class ShortLinkController {

    /**
     * 短链接中心服务调用方法
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinkRemoteService.createShortLink(requestParam);
    }

    /**
     * 短链接分页查询
     */
    @GetMapping("/api/short-link/admin/v1/page")
    private Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkRemoteService.pageShortLink(requestParam);
    }

    /**
     * 修改短链接的：原始链接、有效期类型、有效期、描述
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        return shortLinkRemoteService.updateShortLink(requestParam);
    }

}
