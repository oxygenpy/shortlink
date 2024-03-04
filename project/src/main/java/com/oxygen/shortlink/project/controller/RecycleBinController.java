package com.oxygen.shortlink.project.controller;

import com.oxygen.shortlink.project.common.convention.result.Result;
import com.oxygen.shortlink.project.common.convention.result.Results;
import com.oxygen.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.oxygen.shortlink.project.service.RecycleBinService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJinLong
 * @description 回收站控制层
 * @create 2024-03-04 20:31
 * @date 1.0
 */
@RestController
@AllArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 添加至回收站
     */
    @PostMapping("/api/short-link/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }

}
