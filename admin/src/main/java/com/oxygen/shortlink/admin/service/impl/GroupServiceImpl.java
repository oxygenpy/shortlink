package com.oxygen.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.admin.dao.entity.GroupDO;
import com.oxygen.shortlink.admin.dao.mapper.GroupMapper;
import com.oxygen.shortlink.admin.service.GroupService;
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



}
