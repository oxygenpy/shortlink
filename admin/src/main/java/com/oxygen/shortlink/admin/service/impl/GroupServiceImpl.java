package com.oxygen.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.admin.dao.entity.GroupDO;
import com.oxygen.shortlink.admin.dao.mapper.GroupMapper;
import com.oxygen.shortlink.admin.service.GroupService;
import com.oxygen.shortlink.admin.toolkit.RandomGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
            // TODO username
        }while (hasGid(gid, null));

        GroupDO groupDO = GroupDO.builder().gid(gid).name(groupName).username(null).build();
        baseMapper.insert(groupDO);
    }

    private boolean hasGid(String gid, String username) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class).eq(GroupDO::getGid, gid).eq(GroupDO::getUsername, username);
        GroupDO isHasFlag = baseMapper.selectOne(queryWrapper);
        return isHasFlag != null;
    }

}
