package com.oxygen.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.oxygen.shortlink.admin.common.convention.result.Result;
import com.oxygen.shortlink.admin.common.convention.result.Results;
import com.oxygen.shortlink.admin.dto.req.UserLoginReqDTO;
import com.oxygen.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.oxygen.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.oxygen.shortlink.admin.dto.resp.UserActualRespDTO;
import com.oxygen.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.oxygen.shortlink.admin.dto.resp.UserRespDTO;
import com.oxygen.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiJinLong
 * @description 用户管理控制层
 * @create 2024-01-20 19:55
 * @date 1.0
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable String username) {
        return Results.success(userService.getUserByUsername(username));
    }


    /**
     * 根据用户名查询真实用户
     */
    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/user/")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户
     * @param requestParam
     * @return
     */
    @PutMapping("/api/short-link/v1/user/")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        return Results.success(userService.login(requestParam));
    }

    /**
     * 检查用户是否登录
     * @param username
     * @param token
     * @return
     */
    @GetMapping("/api/short-link/v1/user/check-login")
    private Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        return Results.success(userService.checkLogin(username, token));
    }


    /**
     * 退出登录
     * @param username
     * @param token
     * @return
     */
    @DeleteMapping("/api/short-link/v1/user/logout")
    private Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        userService.logout(username, token);
        return Results.success();
    }
}
