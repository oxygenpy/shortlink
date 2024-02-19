package com.oxygen.shortlink.admin.controller;

import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.common.convention.result.Results;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupReqDTO;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.oxygen.shortlink.admin.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LiJinLong
 * @description 短连接分组控制层
 * @create 2024-02-18 15:26
 * @date 1.0
 */
@RestController
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短连接分组
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupReqDTO requestParam) {
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }

    /**
     * 查询短连接分组集合
     * @return
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改短链接分组名称
     * @param requestParam
     * @return
     */
    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam) {
        groupService.updateGroup(requestParam);
        return Results.success();
    }

}
