package com.oxygen.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 回收站删除短链接
 * @create 2024-03-05 19:50
 * @date 1.0
 */
@Data
public class RecycleBinRemoveReqDTO {

    /**
     * 短链接分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
