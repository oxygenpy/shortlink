package com.oxygen.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.admin.common.convention.exception.ClientException;
import com.oxygen.shortlink.admin.common.convention.exception.ServiceException;
import com.oxygen.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.oxygen.shortlink.admin.dao.entity.UserDO;
import com.oxygen.shortlink.admin.dao.mapper.UserMapper;
import com.oxygen.shortlink.admin.dto.req.UserLoginReqDTO;
import com.oxygen.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.oxygen.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.oxygen.shortlink.admin.dto.resp.UserRespDTO;
import com.oxygen.shortlink.admin.service.GroupService;
import com.oxygen.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.oxygen.shortlink.admin.common.constants.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.oxygen.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * @author LiJinLong
 * @description 用户接口实现层
 * @create 2024-01-20 21:39
 * @date 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    private final RedissonClient redissonClient;

    private final StringRedisTemplate stringRedisTemplate;

    private final GroupService groupService;

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(wrapper);
        if (null == userDO) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    /**
     * 查询用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    /**
     * 注册用户
     *
     * @param requestParam 注册用户所需参数
     */
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            if (lock.tryLock()) {
                try {
                    int insert = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                    if (insert < 1) {
                        throw new ClientException(USER_SAVE_ERROR);
                    }
                } catch (DuplicateKeyException e) {
                    throw new ClientException(USER_EXIST);
                }
                groupService.saveGroup(requestParam.getUsername(), "默认分组");
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }else {
                throw new ClientException(USER_NAME_EXIST);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 修改用户
     *
     * @param requestParam
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
        // todo 验证要改的用户是否是当前登录的用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class).eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    /**
     * 登录用户
     *
     * @param requestParam requestParam 用户名&密码
     * @return token
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }
        String LOGIN_KEY = "login_" + requestParam.getUsername();
        Boolean isTrue = stringRedisTemplate.hasKey(LOGIN_KEY);
        if (isTrue != null && isTrue) {
            throw new ClientException("用户已登录");
        }
        /**
         * Hash
         * Key：login_用户名
         * Value：
         *  Key：token标识
         *  Val：JSON 字符串（用户信息）
         */
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(LOGIN_KEY, uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire(LOGIN_KEY, 30L, TimeUnit.DAYS);
        return new UserLoginRespDTO(uuid);
    }

    /**
     * 检查用户是否登录
     *
     * @param username 用户名
     * @param token    登录token
     * @return
     */
    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login_" + username, token) != null;
    }

    /**
     * 检查用户是否登录
     *
     * @param username 用户名
     * @param token    登录token
     * @return
     */
    @Override
    public void logout(String username, String token) {
        if (!checkLogin(username, token)) {
            throw new ClientException("用户未登录或登录过期");
        }
        stringRedisTemplate.delete("login_" + username);
    }


}
