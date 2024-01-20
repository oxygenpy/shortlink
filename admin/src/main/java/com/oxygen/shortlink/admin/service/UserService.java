package com.oxygen.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oxygen.shortlink.admin.dao.entity.UserDO;
import com.oxygen.shortlink.admin.dto.resp.UserRespDTO;

/**
 * @author LiJinLong
 * @description 用户接口层
 * @create 2024-01-20 21:39
 * @date 1.0
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    UserRespDTO getUserByUsername(String username);
}
