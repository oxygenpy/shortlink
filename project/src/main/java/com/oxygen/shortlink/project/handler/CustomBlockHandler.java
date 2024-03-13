package com.oxygen.shortlink.project.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.oxygen.shortlink.project.common.convention.result.Result;
import com.oxygen.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * @author LiJinLong
 * @description 自定义流控策略
 * @create 2024-03-12 22:07
 * @date 1.0
 */
public class CustomBlockHandler {

    public static Result<ShortLinkCreateRespDTO> createShortLinkBlockHandlerMethod(ShortLinkCreateReqDTO requestParam, BlockException exception) {
        return new Result<ShortLinkCreateRespDTO>().setCode("B100000").setMessage("当前访问网站人数过多，请稍后再试...");
    }
}