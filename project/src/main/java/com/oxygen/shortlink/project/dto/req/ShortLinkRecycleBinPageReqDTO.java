package com.oxygen.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * @author LiJinLong
 * @description 回收站分页参数
 * @create 2024-03-04 22:15
 * @date 1.0
 */
@Data
public class ShortLinkRecycleBinPageReqDTO extends Page {

    /**
     * 分组标识数组
     */
    private List<String> gidList;

}
