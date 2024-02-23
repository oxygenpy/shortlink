package com.oxygen.shortlink.admin.remote;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkGroupCountRespDTO;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.List;

/**
 * @author LiJinLong
 * @description 短链接中心远程调用
 * @create 2024-02-22 14:57
 * @date 1.0
 */
public interface ShortLinKRemoteService {

    /**
     * 创建短链接
     * @param requestParam
     * @return
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO requestParam) {
        String resultStr = HttpUtil.post("http://localhost:8001/api/short-link/v1/create", JSON.toJSONString(requestParam));
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }


    /**
     * 短链接分页查询远程调用
     * @param requestParam
     * @return
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("gid", requestParam.getGid());
        map.put("current", requestParam.getCurrent());
        map.put("size", requestParam.getSize());
        String resultPageStr = HttpUtil.get("http://localhost:8001/api/short-link/v1/page", map);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 查询分组下短链接数量
     * @param requestParam
     * @return
     */
    default Result<List<ShortLinkGroupCountRespDTO>> listGroupShortLinkCount(List<String> requestParam) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("requestParam", requestParam);
        String resultPageStr = HttpUtil.get("http://localhost:8001/api/short-link/v1/count", map);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

}
