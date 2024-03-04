package com.oxygen.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 回收站添加链接实体
 * @create 2024-03-04 20:32
 * @date 1.0
 */
@Data
public class RecycleBinSaveReqDTO {

    /**
     * 短链接分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
