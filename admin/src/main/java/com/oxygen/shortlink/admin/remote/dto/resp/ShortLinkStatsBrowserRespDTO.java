package com.oxygen.shortlink.admin.remote.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiJinLong
 * @description 访问的浏览器来源及数量
 * @create 2024-03-07 21:22
 * @date 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkStatsBrowserRespDTO {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 占比
     */
    private Double ratio;
}