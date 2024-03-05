package com.oxygen.shortlink.project.dto.req;

import lombok.Data;

/**
 * @author LiJinLong
 * @description 恢复短链接
 * @create 2024-03-05 19:22
 * @date 1.0
 */
@Data
public class RecycleBinRecoverReqDTO {

    /**
     * 短链接分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
