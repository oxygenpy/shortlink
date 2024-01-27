package com.oxygen.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oxygen.shortlink.admin.dao.entity.UserDO;
import com.oxygen.shortlink.admin.dto.req.UserLoginReqDTO;
import com.oxygen.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.oxygen.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.UserLoginRespDTO;
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

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    Boolean hasUsername(String username);

    /**
     * 注册用户
     * @param requestParam 注册用户所需参数
     */
    void register(UserRegisterReqDTO requestParam);

    /**
     * 修改用户
     * @param requestParam
     */
    void update(UserUpdateReqDTO requestParam);

    /**
     * 登录用户
     * @param requestParam 用户名&密码
     * @return token
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 检查用户是否登录
     * @param username 用户名
     * @param token
     * @return
     */
    Boolean checkLogin(String username, String token);
}
