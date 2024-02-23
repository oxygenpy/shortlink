package com.oxygen.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oxygen.shortlink.admin.dao.entity.GroupDO;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

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

    /**
     * 新增用户时设置分组
     * @param username
     * @param groupName
     */
    void saveGroup(String username, String groupName);


    /**
     * 查询短连接分组集合
     * @return
     */
    List<ShortLinkGroupRespDTO>  listGroup();

    /**
     * 修改短链接分组名称
     * @param requestParam
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);

    /**
     * 删除短连接分组
     * @param gid 分组标识
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     * @param requestParam
     */
    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam);
}
