package com.oxygen.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.common.convention.result.Results;
import com.oxygen.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.oxygen.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.RecycleBinRemoveReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.oxygen.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
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
@RestController(value = "recycleBinControllerByAdmin")
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 短链接中心服务调用方法
     */
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    /**
     * 添加至回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkActualRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 回收站分页查询
     */
    @GetMapping("/api/short-link/admin/v1/recycle/page")
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        return recycleBinService.pageRecycleBinShortLink(requestParam);
    }

    /**
     * 回收站恢复短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        shortLinkActualRemoteService.recoverRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 回收站恢复短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO requestParam) {
        shortLinkActualRemoteService.removeRecycleBin(requestParam);
        return Results.success();
    }


}
