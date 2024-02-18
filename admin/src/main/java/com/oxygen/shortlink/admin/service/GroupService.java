package com.oxygen.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oxygen.shortlink.admin.dao.entity.GroupDO;

/**
 * @author LiJinLong
 * @description 短连接分组接口层
 * @create 2024-02-18 15:21
 * @date 1.0
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短连接分组
     * @param groupName 短连接分组名称
     */
    void saveGroup(String groupName);
}
