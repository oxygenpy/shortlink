package com.oxygen.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.common.convention.result.Results;
import com.oxygen.shortlink.admin.remote.ShortLinKRemoteService;
import com.oxygen.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJinLong
 * @description 回收站控制层
 * @create 2024-03-04 20:45
 * @date 1.0
 */
@RestController
public class RecycleBinController {

    /**
     * 短链接中心服务调用方法
     */
    ShortLinKRemoteService shortLinKRemoteService = new ShortLinKRemoteService() {};

    /**
     * 添加至回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinKRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 回收站分页查询
     */
    @GetMapping("/api/short-link/admin/v1/recycle/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinKRemoteService.pageRecycleBinShortLink(requestParam);
    }

}
