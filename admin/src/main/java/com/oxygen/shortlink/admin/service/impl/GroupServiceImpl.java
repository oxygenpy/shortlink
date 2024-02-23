package com.oxygen.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.admin.common.biz.user.UserContext;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.dao.entity.GroupDO;
import com.oxygen.shortlink.admin.dao.mapper.GroupMapper;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.oxygen.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.oxygen.shortlink.admin.remote.ShortLinKRemoteService;
import com.oxygen.shortlink.admin.remote.dto.resp.ShortLinkGroupCountRespDTO;
import com.oxygen.shortlink.admin.service.GroupService;
import com.oxygen.shortlink.admin.toolkit.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author LiJinLong
 * @description 短连接分组接口层实现
 * @create 2024-02-18 15:23
 * @date 1.0
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {


    /**
     * 短链接中心服务调用方法
     */
    ShortLinKRemoteService shortLinKRemoteService = new ShortLinKRemoteService() {};

    /**
     * 新增短连接分组
     *
     * @param groupName 短连接分组名称
     */
    @Override
    public void saveGroup(String groupName) {
        saveGroup(UserContext.getUsername(), groupName);
    }

    /**
     * 新增用户时设置分组
     *
     * @param username
     * @param groupName
     */
    @Override
    public void saveGroup(String username, String groupName) {
        String gid = null;
        do {
            gid = RandomGenerator.generateRandom();
        }while (hasGid(gid, username));

        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .username(username)
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
        List<ShortLinkGroupRespDTO> shortLinkGroupRespDTOList = BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
        List<String> gidList = groupDOList.stream().map(GroupDO::getGid).toList();
        Result<List<ShortLinkGroupCountRespDTO>> listResult = shortLinKRemoteService.listGroupShortLinkCount(gidList);
        shortLinkGroupRespDTOList.stream().map(each -> {
            listResult.getData().stream().filter(data -> Objects.equals(data.getGid(), each.getGid())).forEach(data -> each.setShortLinkCount(data.getShortLinkCount()));
            return each;
        }).collect(Collectors.toList());

        return shortLinkGroupRespDTOList;
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

    /**
     * 删除短连接分组
     *
     * @param gid 分组标识
     */
    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, updateWrapper);
    }

    /**
     * 短链接分组排序
     *
     * @param requestParam
     */
    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam) {
        for (ShortLinkGroupSortReqDTO dto : requestParam) {
            lambdaUpdate().eq(GroupDO::getGid, dto.getGid())
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .set(GroupDO::getSortOrder, dto.getSortOrder()).update();
        }
    }

    private boolean hasGid(String gid, String username) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, username);
        GroupDO isHasFlag = baseMapper.selectOne(queryWrapper);
        return isHasFlag != null;
    }

}
