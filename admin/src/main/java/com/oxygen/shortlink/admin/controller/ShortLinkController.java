package com.oxygen.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.remote.ShortLinKRemoteService;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
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
    ShortLinKRemoteService shortLinKRemoteService = new ShortLinKRemoteService() {};

    /**
     * 创建短链接
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinKRemoteService.createShortLink(requestParam);
    }

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/page")
    private Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinKRemoteService.pageShortLink(requestParam);
    }
}
