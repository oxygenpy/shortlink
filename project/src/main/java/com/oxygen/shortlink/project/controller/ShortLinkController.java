package com.oxygen.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.project.common.convention.result.Result;
import com.oxygen.shortlink.project.common.convention.result.Results;
import com.oxygen.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkGroupCountRespDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.oxygen.shortlink.project.service.ShortLinkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LiJinLong
 * @description 短链接控制层
 * @create 2024-02-19 17:31
 * @date 1.0
 */

@RestController
@AllArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    /**
     * 创建短链接
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/v1/page")
    private Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }


    /**
     * 查询分组下短链接数量
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/v1/count")
    private Result<List<ShortLinkGroupCountRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam) {
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
    }

}
