package com.oxygen.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author LiJinLong
 * @description 短连接分页参数
 * @create 2024-02-21 20:10
 * @date 1.0
 */
@Data
public class ShortLinkPageReqDTO extends Page {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序标识
     */
    private String orderTag;
}
