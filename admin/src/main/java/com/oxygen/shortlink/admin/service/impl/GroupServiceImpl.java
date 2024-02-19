package com.oxygen.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.admin.common.biz.user.UserContext;
import com.oxygen.shortlink.admin.dao.entity.GroupDO;
import com.oxygen.shortlink.admin.dao.mapper.GroupMapper;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.oxygen.shortlink.admin.service.GroupService;
import com.oxygen.shortlink.admin.toolkit.RandomGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiJinLong
 * @description 短连接分组接口层实现
 * @create 2024-02-18 15:23
 * @date 1.0
 */
@Service
@AllArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {


    /**
     * 新增短连接分组
     *
     * @param groupName 短连接分组名称
     */
    @Override
    public void saveGroup(String groupName) {
        String gid = null;
        do {
            gid = RandomGenerator.generateRandom();
        }while (hasGid(gid, UserContext.getUsername()));

        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .username(UserContext.getUsername())
                .sortOrder(0)
                .build();
        baseMapper.insert(groupDO);
    }

    /**
     * 查询短连接分组集合
     *
     * @return
     */
    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
    }

    /**
     * 修改短链接分组名称
     *
     * @param requestParam
     */
    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, requestParam.getGid())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = GroupDO.builder().name(requestParam.getName()).build();
        baseMapper.update(groupDO, updateWrapper);
    }

    private boolean hasGid(String gid, String username) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, username);
        GroupDO isHasFlag = baseMapper.selectOne(queryWrapper);
        return isHasFlag != null;
    }

}
